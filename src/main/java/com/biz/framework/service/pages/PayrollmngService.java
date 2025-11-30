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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PayrollmngService {

    private final PayrollmngMapper payrollmngMapper;
    private final MileageHisMapper mileageHisMapper;

    public Map<String, Object> findPayrollList(SettlementmstDto settlementmstDto) {
        Long mileage = payrollmngMapper.findCustSumMileage(settlementmstDto);

        List<CamelCaseMap> payrollList = payrollmngMapper.findPayrollList(settlementmstDto);

        String totalProfit = payrollList.stream()
                // 각 행에서 (판매총액 – 상품총액) 계산
                .map(map -> {
                    Object val = map.get("prodCostAmt");
                    return val != null ? new BigDecimal(val.toString()) : BigDecimal.ZERO;
                })
                // 전부 더해서 한 개의 BigDecimal로
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                // 문자열로 변환
                .toPlainString();

        Map<String, Object> result = new HashMap<>();
        result.put("data", payrollList);
        result.put("totalMileage", mileage);
        result.put("totalProfit", totalProfit);

        return result;
    }

    public int payrollApplypayment(SettlementmstDto settlementmstDto) {

        int result = 0;

        // 입금확인시 실제입금금액 및 상태 변경
        List<SettlementmstDto> settlementMstDtoList = settlementmstDto.getSettlementmstDtoList();
        for (SettlementmstDto dto : settlementMstDtoList) {

            try {

                BigDecimal confirmAmt = new BigDecimal(dto.getConfirmAmt());         // 확정 금액
                BigDecimal confirmRateAmt = new BigDecimal(dto.getConfirmRateAmt()); // 확정 수수료
                BigDecimal confirmRate = new BigDecimal(dto.getIncentiveRate());     // 확정 인센률
                BigDecimal finalRate = new BigDecimal(dto.getFinalRate());           // 변경 인센률

                // 인센률이 0보다 커야만 변경 수수료 계산
                if (finalRate.signum() > 0) {
                    BigDecimal finalAmt;
                    if ("CVQ".equals(dto.getReqGubun())) {
                        finalAmt = calculateFinalAmtForCVQ(confirmAmt, finalRate);
                    } else {
                        finalAmt = calculateFinalAmtDefault(confirmRateAmt, confirmRate, finalRate);
                    }

                    // DTO에 결과 세팅
                    dto.setFinalRate(finalRate.toPlainString());
                    dto.setFinalAmt(finalAmt.toPlainString());
                }

                dto.setPayYm(settlementmstDto.getPayYm());

                result = payrollmngMapper.confirmApplypayment(dto);

            } catch (Exception e) {
                // 파싱 오류 또는 계산 오류 처리
                throw new ServiceException("급여반영 도중 오류가 발생했습니다.");
                // 로그 출력 등 필요 시 추가
            }
        }

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

    public List<SettlementmstDto.TbSettlementRefundItemDto> findRefundItemsBySettlementSeq(SettlementmstDto.TbSettlementRefundItemDto tbSettlementRefundItemDto) {
        return payrollmngMapper.findRefundItemsBySettlementSeq(tbSettlementRefundItemDto);
    }

    /**
     * CVQ 건에 대한 변경 수수료 계산:
     *    (확정 수수료 / 1.1) × 변경 인센률
     */
    private BigDecimal calculateFinalAmtForCVQ(BigDecimal confirmRateAmt, BigDecimal finalRate) {
        return confirmRateAmt
                .multiply(finalRate)
                .divide(BigDecimal.valueOf(1.1), 0, RoundingMode.HALF_UP);
    }

    /**
     * 일반 건에 대한 변경 수수료 계산:
     *    영업이익 = 확정수수료 × 1.1 / 확정 인센률
     *    변경 수수료 = 영업이익 × 변경 인센률 / 1.1
     */
    private BigDecimal calculateFinalAmtDefault(BigDecimal confirmRateAmt, BigDecimal confirmRate, BigDecimal finalRate) {
        BigDecimal profit = confirmRateAmt
                .multiply(BigDecimal.valueOf(1.1))
                .divide(confirmRate, 10, RoundingMode.HALF_UP);

        return profit
                .multiply(finalRate)
                .divide(BigDecimal.valueOf(1.1), 0, RoundingMode.HALF_UP);
    }
}
