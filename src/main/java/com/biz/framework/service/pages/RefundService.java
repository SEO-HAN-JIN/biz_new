package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.RefundMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class RefundService {

    private final ApplypaymentMapper applypaymentMapper;
    private final RefundMapper refundMapper;

    public List<CamelCaseMap> findApplypayment(SettlementDto settlementDto) {
        return applypaymentMapper.findApplypayment(settlementDto);
    }

    public int saveRefund(SettlementDto settlementDto) {
        return applypaymentMapper.saveApplypayment(settlementDto);
    }

    public int deleteCustomer(CustomerDto customerDto) {
        return applypaymentMapper.deleteCustomer(customerDto);
    }

    public List<CamelCaseMap> findRefund(SettlementDto settlementDto) {
        return refundMapper.findRefund(settlementDto);
    }

    public List<CamelCaseMap> findSettlement(SettlementDto settlementDto) {
        return refundMapper.findSettlement(settlementDto);
    }
}
