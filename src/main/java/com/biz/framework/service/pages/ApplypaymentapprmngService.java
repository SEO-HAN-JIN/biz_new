package com.biz.framework.service.pages;

import ch.qos.logback.core.util.StringUtil;
import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.ApplypaymentapprmngMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentapprmngService {

    private final ApplypaymentapprmngMapper applypaymentapprmngMapper;
    private final MileageHisMapper mileageHisMapper;
    private final CustomerMapper customerMapper;

    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngMapper.findApplypaymentmngList(settlementDto);
    }

    public int confirmApplypayment(SettlementmstDto settlementmstDto) {

        int result = 0;

        SettlementDto settlementDto = findSettlement(settlementmstDto);

        if(!ObjectUtils.isEmpty(settlementDto)) {

            // 승인완료
            settlementDto.setApplyStatus("02");

            // 승인SEQ 생성
            String confirmSeq = applypaymentapprmngMapper.createConfirmSeq(settlementmstDto);
            settlementmstDto.setConfirmSeq(confirmSeq);
            settlementmstDto.setUserId(settlementDto.getUserId());
            settlementmstDto.setCustId(settlementDto.getCustId());

            int mileageAmt = 0;

            //환불의 경우
            if("Y".equals(settlementDto.getRefundInd()))
            {
                settlementmstDto.setConfirmAmt(settlementDto.getRefundProdTotalAmt());      // 환불금액(상품가)
                settlementmstDto.setConfirmRateAmt(settlementDto.getRefundExpectRateAmt()); // 확정수수료

                if ("04".equals(settlementDto.getGubun())) {
                    settlementmstDto.setConfirmMileage(settlementDto.getRefundProdTotalAmt());  // 환불금액(판매가기준) 적립
                }
            }

            // 입금확인 마스터 저장
            result = applypaymentapprmngMapper.confirmApplypaymentmst(settlementmstDto);

            if (result > 0) {

                if (!StringUtil.isNullOrEmpty(settlementmstDto.getConfirmMileage()))
                {
                    mileageAmt = Integer.parseInt(settlementmstDto.getConfirmMileage());
                }

                // 누적마일리지가 있을 경우 고객 마일리지 누적 HIS UPDATE
                if (mileageAmt > 0) {

                    customerMapper.updateFinalMileage(settlementmstDto.getCustId(), mileageAmt);

                    MileageHisDto mileageHisDto = new MileageHisDto();
                    mileageHisDto.setBizNo(settlementmstDto.getCustId());           // 고객 key
                    mileageHisDto.setEmpId(settlementmstDto.getUserId());           // 담당자 ID
                    mileageHisDto.setSettlementSeq(confirmSeq);                     // 승인 SEQ
                    mileageHisDto.setMileageAmt(mileageAmt);
                    mileageHisDto.setCreatedPage("SC");                             // 정산승인
                    mileageHisDto.setCreatedId(settlementmstDto.getLoginUserId());
                    mileageHisDto.setReason(settlementDto.getGubunName());
                    mileageHisMapper.addMileageHistory(mileageHisDto);
                }

                result = applypaymentapprmngMapper.updateSettlement(settlementDto);
            }
            else
                throw new ServiceException("처리 도중 오류가 발생했습니다.");
        }

        if(result == 0)
            throw new ServiceException("처리 도중 오류가 발생했습니다11.");

        return result;

    }

    public int cancelApplypayment(SettlementmstDto settlementmstDto) {

        int result = 0;
        if(applypaymentapprmngMapper.checkPayrollInd(settlementmstDto) >= 0)
            throw new ServiceException("이미 급여처리가 진행된 요청 건입니다.");

        // 결재완료되서 settlementmst 테이블 데이터 여부 체크
        if(applypaymentapprmngMapper.checkSettlementmst(settlementmstDto) > 0) {
            applypaymentapprmngMapper.cancelApplypayment(settlementmstDto);
        }

        SettlementDto settlementDto = new SettlementDto();
        settlementDto.setSettlementSeq(settlementmstDto.getSettlementSeq());
        settlementDto.setApplyStatus("03");

        result = applypaymentapprmngMapper.updateSettlement(settlementDto);

        return result;
    }

    public SettlementDto findSettlement(SettlementmstDto settlementmstDto)
    {
        return applypaymentapprmngMapper.findSettlement(settlementmstDto);
    }
}
