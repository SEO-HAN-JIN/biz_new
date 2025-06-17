package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.TaxinvoicesDto;
import com.biz.framework.dto.pages.TaxinvoiceslineDto;
import com.biz.framework.dto.system.CompanyDto;
import com.biz.framework.mapper.pages.TaxinvoicesMapper;
import com.popbill.api.IssueResponse;
import com.popbill.api.PopbillException;
import com.popbill.api.Response;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.MgtKeyType;
import com.popbill.api.taxinvoice.Taxinvoice;
import com.popbill.api.taxinvoice.TaxinvoiceDetail;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaxinvoicesService {

    private final TaxinvoicesMapper taxinvoicesMapper;

    private final TaxinvoiceService popbillSvc;

    // 프록시된 자기 자신
    private final TaxinvoicePreparer taxSvc;

    public List<CamelCaseMap> findTaxinvoicesList(TaxinvoicesDto taxinvoicesDto) {
        return taxinvoicesMapper.findTaxinvoicesList(taxinvoicesDto);
    }

    public TaxinvoicesDto loadTaxvoicesInfo(TaxinvoicesDto taxinvoicesDto) {

        TaxinvoicesDto form = new TaxinvoicesDto();

        if (!ObjectUtils.isEmpty(taxinvoicesDto)) {

            TaxinvoicesDto custInfo = taxinvoicesMapper.findCustInfo(taxinvoicesDto);

            // 고객정보 생성
            form.setCustName(custInfo.getCustName());
            form.setBizNo(custInfo.getBizNo());
            form.setUserId(custInfo.getUserId());
            form.setCustAddr(custInfo.getCustAddr());
            form.setCustTel(custInfo.getCustTel());
            form.setCustMail(custInfo.getCustMail());
            form.setOwnerName(custInfo.getOwnerName());

            BigDecimal totalSupply = BigDecimal.ZERO;
            BigDecimal totalTax    = BigDecimal.ZERO;

            String confirmSeq = null;
            // Formatter: 소수점 이하 내림, 3자리마다 콤마
            DecimalFormat df = new DecimalFormat("#,###");

            if(CollectionUtils.isNotEmpty(taxinvoicesDto.getTaxinvoicesDtoList())) {

                List<TaxinvoiceslineDto> lineList = new ArrayList<>();

                for (TaxinvoicesDto dto : taxinvoicesDto.getTaxinvoicesDtoList()) {
                    // 정산승인 및 요청정보가져오기
                    TaxinvoicesDto req = taxinvoicesMapper.findTaxinvoicesDetail(dto);

                    // 금액 계산
                    BigDecimal total = new BigDecimal(req.getConfirmAmt());
                    BigDecimal supply = total.divide(BigDecimal.valueOf(1.1), 2, BigDecimal.ROUND_HALF_UP);
                    BigDecimal tax    = total.subtract(supply);

                    totalSupply = totalSupply.add(supply);
                    totalTax    = totalTax.add(tax);

                    // 기본 품목 라인 생성
                    TaxinvoiceslineDto line = new TaxinvoiceslineDto();
                    line.setConfirmSeq(req.getConfirmSeq());
                    line.setDescription("마케팅");
                    line.setQuantity("1");
                    line.setUnitPrice(df.format(total));
                    line.setSupplyAmount(df.format(supply));
                    line.setTaxAmount(df.format(tax));

                    lineList.add(line);
                }

                form.setConfirmSeq(lineList.stream().map(seq -> seq.getConfirmSeq()).collect(Collectors.joining(",")));

                form.setTaxinvoiceslineDtoList(lineList);
                form.setSupplyAmount(df.format(totalSupply));
                form.setTaxAmount(df.format(totalTax));

                // 총합계 (공급가액 + 세액)
                BigDecimal totalAmount = totalSupply.add(totalTax);
                form.setTotalAmount(df.format(totalAmount));

            }
        }

        return form;
    }

    /**
     * 다건 settlementSeq 리스트 기반 다중 폼 DTO 구성
     */
    public TaxinvoicesDto loadFormList(TaxinvoicesDto taxinvoicesDto) {
        return loadTaxvoicesInfo(taxinvoicesDto);
    }

    /**
     * 세금계산서 발행 로직
     */
    @Transactional
    public int createInvoices(TaxinvoicesDto form){

        // 1) 실제 폼 데이터 구성
        if (CollectionUtils.isEmpty(form.getTaxinvoiceslineDtoList())) {
            throw new ServiceException("세금계산서 발행 정보가 없습니다.");
        }

        CompanyDto companyInfo = taxinvoicesMapper.findCompanyInfo(form);

        if(StringUtils.isEmpty(companyInfo.getBizNo()))
            throw new ServiceException("회사 사업자번호를 확인해주세요.");

        String bizNo = companyInfo.getBizNo().replaceAll("-", "");

        //테스트용 사업자번호
        form.setBizNo("8888888888");

        // 정산요청건 초기 INSERT
        taxSvc.updateTaxinvoiceBefore(form);

        // 세금계산서 데이터 주입
        Taxinvoice tx = buildPopbillTaxinvoice(form, companyInfo, bizNo);

        try {
            String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            // 팝빌 즉시발행
            IssueResponse resp = popbillSvc.registIssue(
                    bizNo,    // 우리 회사 사업자번호
                    tx,
                    false,      // 거래명세서 동시작성 여부
                    "",                 // 메모
                    false,              // 강제발행 여부
                    null                // 거래명세서 문서번호
            );

            // 세금계산서 발급번호
            form.setInvoiceNo(resp.getNtsConfirmNum());
            form.setIssueDate(today);

        } catch (PopbillException e) {

            form.setRemark("[" + e.getCode() + "] " + e.getMessage());
            taxSvc.cancelTaxinvoice(form);

            throw new RuntimeException(
                    "세금계산서 발행 실패 \r\n " +
                            " code=" + e.getCode() + " message=" + e.getMessage(), e
            );
        }

        // taxinvoice 메인 테이블 저장
        List<String> seqList = Arrays.stream(form.getConfirmSeq().split(","))
                .map(String::trim)
                .filter(s->!s.isEmpty())
                .collect(Collectors.toList());

        // 정산요청건 상태 업데이트
        if(updateTaxinvoiceAfter(form, seqList) <= 0)
        {
            try {
                Response cel = popbillSvc.cancelIssue(bizNo, MgtKeyType.SELL, form.getTaxKey(), "시스템 오류로 취소");
                form.setRemark("[정산요청 상태 업데이트 도중 오류 발생] " + cel.getMessage());
                taxSvc.cancelTaxinvoice(form);
            }
            catch (PopbillException e) {

                form.setRemark("[" + e.getCode() + "] " + e.getMessage());
                taxSvc.cancelTaxinvoice(form);

                throw new RuntimeException(
                        "세금계산서 취소 실패: confirmSeq=" + form.getConfirmSeq() +
                                " code=" + e.getCode() + " message=" + e.getMessage(), e
                );
            }
        }

        return 1;
    }

    public Taxinvoice buildPopbillTaxinvoice(TaxinvoicesDto form, CompanyDto companyInfo, String bizNo)
    {
        // 2) 팝빌 Taxinvoice 객체 세팅
        Taxinvoice tx = new Taxinvoice();
        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        tx.setWriteDate(today);
        tx.setChargeDirection("정과금");
        tx.setIssueType("정발행");
        tx.setPurposeType("영수");
        tx.setTaxType("과세");

        // 공급자(우리 회사)
        tx.setInvoicerCorpNum(bizNo);
        tx.setInvoicerMgtKey(form.getTaxKey()); // 키로 confirmSeq(콤마분리)를 그대로 사용
        // ▶ 나머지 공급자 정보는 설정파일 또는 별도 서비스에서 조회하세요
        tx.setInvoicerCorpName(companyInfo.getCoName());
        tx.setInvoicerCEOName(companyInfo.getCeoName());
        tx.setInvoicerAddr(companyInfo.getCompanyAddress());
        tx.setInvoicerBizType(companyInfo.getBizType());
        tx.setInvoicerBizClass(companyInfo.getBizClass());
        //tx.setInvoicerContactName("");
        tx.setInvoicerEmail(companyInfo.getCompanyMail());
        tx.setInvoicerTEL(companyInfo.getCompanyTel());
        //tx.setInvoicerHP("010-1234-5678");
        tx.setInvoicerSMSSendYN(false);

        // 공급받는자(고객)
        tx.setInvoiceeType("사업자");
        tx.setInvoiceeCorpNum(form.getBizNo());
        tx.setInvoiceeCorpName(form.getCustName());
        tx.setInvoiceeCEOName(form.getOwnerName());
        tx.setInvoiceeAddr(form.getCustAddr());
        tx.setInvoiceeBizType(form.getBizType());
        tx.setInvoiceeBizClass(form.getBizClass());
        tx.setInvoiceeContactName1(form.getOwnerName());
        tx.setInvoiceeEmail1(form.getCustMail());
        tx.setInvoiceeTEL1(form.getCustTel());
        //tx.setInvoiceeHP1(form.getCustTel());
        tx.setInvoiceeSMSSendYN(false);

        // 금액 (화면에서 콤마가 붙은 문자열이기 때문에 숫자만 남기려면 아래처럼 치환)
        tx.setSupplyCostTotal(form.getSupplyAmount().replaceAll(",", ""));
        tx.setTaxTotal(form.getTaxAmount().replaceAll(",", ""));
        tx.setTotalAmount(form.getTotalAmount().replaceAll(",", ""));

        // 상세항목(품목) 리스트
        List<TaxinvoiceDetail> details = new ArrayList<>();
        short seq = 1;
        for (TaxinvoiceslineDto line : form.getTaxinvoiceslineDtoList()) {
            TaxinvoiceDetail d = new TaxinvoiceDetail();
            d.setSerialNum(seq++);
            // 거래일자: yyyyMMdd
            d.setPurchaseDT(today);
            d.setItemName(line.getDescription());
            d.setQty(line.getQuantity());
            d.setUnitCost(line.getUnitPrice().replaceAll(",", ""));
            d.setSupplyCost(line.getSupplyAmount().replaceAll(",", ""));
            d.setTax(line.getTaxAmount().replaceAll(",", ""));
            d.setRemark(line.getDescription());
            details.add(d);
        }
        tx.setDetailList(details);

        // 추가담당자 없을 경우 빈 리스트
        tx.setAddContactList(Collections.emptyList());

        return tx;
    }

    public int updateTaxinvoiceAfter(TaxinvoicesDto form, List<String> confirmSeqList) {

        int result = 0;

        if(taxinvoicesMapper.updateTaxinvoice(form) > 0)
        {
            Map<String, Object> params = new HashMap<>();
            params.put("taxKey", form.getTaxKey());
            params.put("issueDate", form.getIssueDate());
            params.put("taxInd", "Y");
            params.put("confirmSeqList", confirmSeqList);

            result = taxinvoicesMapper.updateSettlement(params);
        }

        return result;
    }

    public String getTaxinvoicePopupUrl(TaxinvoicesDto dto) {

        CompanyDto companyInfo = taxinvoicesMapper.findCompanyInfo(dto);
        String corpNum = companyInfo.getBizNo().replaceAll("-", "");
        String taxKey = dto.getTaxKey();

        try {
            // 3번째 파라미터로 userId를 넘겨야 해당 사용자의 권한으로 팝업이 뜹니다.
            return popbillSvc.getViewURL(corpNum, MgtKeyType.SELL, taxKey, "");
        } catch (PopbillException e) {
            throw new ServiceException("세금계산서 팝업 URL 생성 실패: code="
                    + e.getCode() + " msg=" + e.getMessage(), e);
        }
    }

    public int taxCancelIssue(TaxinvoicesDto form)
    {
        try {
            Response cel = popbillSvc.cancelIssue(form.getBizNo(), MgtKeyType.SELL, form.getTaxKey(), "시스템 오류로 취소");
            form.setRemark("[정산요청 상태 업데이트 도중 오류 발생] " + cel.getMessage());
            taxSvc.cancelTaxinvoice(form);
        }
        catch (PopbillException e) {

            form.setRemark("[" + e.getCode() + "] " + e.getMessage());
            taxSvc.cancelTaxinvoice(form);

            throw new RuntimeException(
                    "세금계산서 취소 실패: confirmSeq=" + form.getConfirmSeq() +
                            " code=" + e.getCode() + " message=" + e.getMessage(), e
            );
        }

        return 1;
    }


}
