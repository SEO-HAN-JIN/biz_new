package com.biz.framework.service.pages;

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

    public int saveApplypayment(SettlementDto settlementDto) {
        settlementDto.setReqGubun("AQ");        // 요청구분(SE01) : AQ(정산요청)
        settlementDto.setApplyStatus("01");     // 승인여부(SE04) : 01(승인요청)
        return applypaymentMapper.saveApplypayment(settlementDto);
    }

    public int deleteCustomer(CustomerDto customerDto) {
        return applypaymentMapper.deleteCustomer(customerDto);
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return applypaymentMapper.findSettlement(settlementDto);
    }

    public double findIncentiveRate(SettlementDto settlementDto) {
        return applypaymentMapper.findIncentiveRate(settlementDto);
    }
}
