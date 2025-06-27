package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentService {

    private final ApplypaymentMapper applypaymentMapper;
    private final CustomerMapper customerMapper;
    private final MileageHisMapper mileageHisMapper;

    public Map<String, Object> findApplypayment(SettlementDto settlementDto) {

        List<CamelCaseMap> list = applypaymentMapper.findApplypayment(settlementDto);

        // 전체 잔여마일리지 합산
        int mileage = applypaymentMapper.findCustSumMileage(settlementDto);

        String totalProfit = list.stream()
                // 각 행에서 (판매총액 – 상품총액) 계산
                .map(map -> {
                    BigDecimal sale  = new BigDecimal(Objects.toString(map.get("saleTotalAmt"), "0"));
                    BigDecimal prod  = new BigDecimal(Objects.toString(map.get("prodTotalAmt"),   "0"));
                    return sale.subtract(prod);
                })
                // 전부 더해서 한 개의 BigDecimal로
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                // 문자열로 변환
                .toPlainString();

        Map<String, Object> result = new HashMap<>();
        result.put("data", list);
        result.put("totalMileage", mileage);
        result.put("totalProfit", totalProfit);
        return result;
    }

    /*
        환불요청인 경우에도 해당 메서드 사용 중 (컨트롤러에서 요청구분, 승인여부 세팅)
     */
    public int saveApplypayment(SettlementDto settlementDto) {
        int result = 0;

        switch (settlementDto.getRowStatus()) {
            case C -> {
                // 정산번호 가져오기
                String settlementSeq = applypaymentMapper.createSettlementSeq(settlementDto);

                // 정산요청 저장
                settlementDto.setSettlementSeq(settlementSeq);
                result += applypaymentMapper.saveApplypayment(settlementDto);

                // 킵사용시 마일리지 유효성 검사 및 저장
                if ("Y".equals(settlementDto.getMileageUseInd())) {
                    saveMileageAq(settlementDto);
                }
            }
            case U -> {
                result += applypaymentMapper.updateApplypayment(settlementDto);

                if ("Y".equals(settlementDto.getMileageUseInd())) {
                    saveMileageAq(settlementDto);
                }
            }
        }
        return result;
    }

    public int deleteSettlement(SettlementDto settlementDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(settlementDto.getSettlementDtoList()))
        {
            for(SettlementDto dto : settlementDto.getSettlementDtoList())
            {
                if (!"C".equals(applypaymentMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("요청취소건만 삭제 가능합니다.");
                }

                result += applypaymentMapper.deleteSettlement(dto);
            }
        }


        return result;
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return applypaymentMapper.findSettlement(settlementDto);
    }

    public double findIncentiveRate(SettlementDto settlementDto) {
        return applypaymentMapper.findIncentiveRate(settlementDto);
    }

    private void saveMileageAq(SettlementDto settlementDto) {

        // 고객정보 가져오기
        CustomerDto customerDto = new CustomerDto();
        customerDto.setBizNo(settlementDto.getCustId());
        CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

        String bizNo = (String) customerInfo.get("bizNo");
        int existMileage = Integer.parseInt((String) customerInfo.get("mileage"));  // 기존 고객 잔여 마일리지
        int useMileage = -(Integer.parseInt(settlementDto.getUseMileage()));        // 사용할 마일리지
        int restMileage = existMileage + useMileage;                                // 잔여 마일리지 - 사용 마일리지

        if (restMileage < 0) {
            NumberFormat currencyInstance = NumberFormat.getCurrencyInstance(Locale.KOREA);
            String formattedExistMileage = currencyInstance.format(existMileage).replace("₩", "");
            String formattedUseMileage = currencyInstance.format(useMileage).replace("₩", "");;
            throw new ServiceException("킵 사용금액이 잔여킵금액보다 같거나 더 적어야 합니다.\n잔여킵금액: " + formattedExistMileage + "\n킵사용금액: " + formattedUseMileage);
        }

        customerMapper.updateFinalMileage(settlementDto.getLoginCoId(), bizNo, useMileage);
        MileageHisDto mileageHisDto = new MileageHisDto();
        mileageHisDto.setBizNo(bizNo);
        mileageHisDto.setEmpId(settlementDto.getUserId());
        mileageHisDto.setSettlementSeq(settlementDto.getSettlementSeq());
        mileageHisDto.setMileageAmt(useMileage);
        mileageHisDto.setCreatedPage("AQ"); // 정산요청: AQ
        mileageHisDto.setCreatedId(settlementDto.getLoginUserId());
        mileageHisMapper.addMileageHistory(mileageHisDto);
    }

    public int cancelSettlement(SettlementDto settlementDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(settlementDto.getSettlementDtoList()))
        {
            for(SettlementDto dto : settlementDto.getSettlementDtoList())
            {
                if (!"01".equals(applypaymentMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("승인요청건만 삭제 가능합니다.");
                }

                result += applypaymentMapper.cancelSettlement(dto);

                if ("Y".equals(dto.getMileageUseInd())) {
                    // 고객정보 가져오기
                    CustomerDto customerDto = new CustomerDto();
                    customerDto.setBizNo(dto.getCustId());
                    CamelCaseMap customerInfo = customerMapper.findCustomerInfo(customerDto);

                    String bizNo = (String) customerInfo.get("bizNo");
                    int useMileage = Integer.parseInt(dto.getUseMileage());

                    customerMapper.updateFinalMileage(dto.getLoginCoId(), bizNo, useMileage);

                    if (useMileage > 0) {
                        MileageHisDto mileageHisDto = new MileageHisDto();
                        mileageHisDto.setBizNo(bizNo);
                        mileageHisDto.setEmpId(dto.getUserId());
                        mileageHisDto.setSettlementSeq(dto.getSettlementSeq());
                        mileageHisDto.setMileageAmt(useMileage);
                        mileageHisDto.setCreatedPage("CQ"); // 취소요청: CQ
                        mileageHisDto.setCreatedId(dto.getLoginUserId());

                        mileageHisMapper.addMileageHistory(mileageHisDto);
                    }
                }
            }
        }


        return result;
    }
}
