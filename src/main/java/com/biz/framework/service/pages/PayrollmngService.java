package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.MileageHisMapper;
import com.biz.framework.mapper.pages.PayrollmngMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

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

    public int cancelApplypayment(SettlementmstDto settlementmstDto)
    {
        int result = 0;

        // 입금확인시 실제입금금액 및 상태 변경
        List<SettlementmstDto> settlementMstDtoList = settlementmstDto.getSettlementmstDtoList();

        List<SettlementmstDto> uniqueUserPayList = settlementMstDtoList.stream()
                .collect(Collectors.collectingAndThen(
                        Collectors.toMap(
                                dto -> dto.getUserId() + "_" + dto.getPayYm(),
                                dto -> {
                                    SettlementmstDto s = new SettlementmstDto();
                                    s.setUserId(dto.getUserId());
                                    s.setPayYm(dto.getPayYm());
                                    return s;
                                },
                                (existing, replacement) -> existing,
                                LinkedHashMap::new
                        ),
                        map -> new ArrayList(map.values())
                ));

        // 결과 확인
        for(SettlementmstDto dto : uniqueUserPayList)
        {
            if(payrollmngMapper.checkPayroll(dto) > 0)
                throw new ServiceException("추가 수당이 작업된 이력이 있습니다.\n급여지급관리에서 삭제 후 취소 부탁드립니다.\n\n- 사용자 ID : " + dto.getUserId() + " \n- 급여지급월 : " + dto.getPayYm());
        }

        for (SettlementmstDto dto : settlementMstDtoList) {
            result = payrollmngMapper.cancelApplypayment(dto);
        }

        return result;
    }
}
