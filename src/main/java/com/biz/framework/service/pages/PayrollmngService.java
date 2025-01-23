package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.MileageHisMapper;
import com.biz.framework.mapper.pages.PayrollmngMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollmngService {

    private final PayrollmngMapper payrollmngMapper;
    private final MileageHisMapper mileageHisMapper;

    public List<CamelCaseMap> findPayrollList(SettlementmstDto settlementmstDto) {
        return payrollmngMapper.findPayrollList(settlementmstDto);
    }

    @Transactional
    public int payrollApplypayment(SettlementmstDto settlementmstDto) {

        int result = 0;

        // 입금확인시 실제입금금액 및 상태 변경
        List<SettlementmstDto> settlementMstDtoList = settlementmstDto.getSettlementmstDtoList();
        for (SettlementmstDto dto : settlementMstDtoList) {

            dto.setPayYm(settlementmstDto.getPayYm());

            result = payrollmngMapper.confirmApplypayment(dto);
        }

//        if(result > 0) {
//            int mileageAmt = 0;
//            if (!StringUtil.isNullOrEmpty(settlementDto.getSaveMileage()))
//                mileageAmt = Integer.parseInt(settlementDto.getSaveMileage());
//
//            // 누적마일리지가 있을 경우 고객 마일리지 누적 HIS UPDATE
//            if (mileageAmt > 0) {
//
//                MileageHisDto mileageHisDto = new MileageHisDto();
//                mileageHisDto.setBizNo(settlementDto.getCustId());          // 고객 key
//                mileageHisDto.setEmpId(settlementDto.getUserId());          // 담당자 ID
//                mileageHisDto.setMileageAmt(mileageAmt);
//                mileageHisDto.setCreatedPage("SC");     // 정산승인
//                mileageHisDto.setCreatedId(settlementDto.getLoginUserId());
//                result += mileageHisMapper.addMileageHistory(mileageHisDto);
//            }
//        }

        return result;

    }
}
