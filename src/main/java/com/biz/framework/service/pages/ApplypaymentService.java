package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentService {

    private final ApplypaymentMapper applypaymentMapper;

    public List<CamelCaseMap> findApplypayment(SettlementDto settlementDto) {
        return applypaymentMapper.findApplypayment(settlementDto);
    }

    /*
        환불요청인 경우에도 해당 메서드 사용 중 (컨트롤러에서 요청구분, 승인여부 세팅)
     */
    public int saveApplypayment(SettlementDto settlementDto) {
        int result = 0;
        switch (settlementDto.getRowStatus()) {
            case C -> result += applypaymentMapper.saveApplypayment(settlementDto);
            case U -> result += applypaymentMapper.updateApplypayment(settlementDto);
        }
        return result;
    }

    public int deleteSettlement(SettlementDto settlementDto) {
        if (!"01".equals(applypaymentMapper.checkApplyStatus(settlementDto))) {
            throw new ServiceException("승인요청건만 삭제 가능합니다.");
        }
        return applypaymentMapper.deleteSettlement(settlementDto);
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return applypaymentMapper.findSettlement(settlementDto);
    }

    public double findIncentiveRate(SettlementDto settlementDto) {
        return applypaymentMapper.findIncentiveRate(settlementDto);
    }
}
