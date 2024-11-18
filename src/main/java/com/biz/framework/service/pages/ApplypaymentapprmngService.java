package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.ApplypaymentapprmngMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ApplypaymentapprmngService {

    private final ApplypaymentapprmngMapper applypaymentapprmngMapper;

    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngMapper.findApplypaymentmngList(settlementDto);
    }

    public int confirmApplypayment(SettlementDto settlementDto) {
        return applypaymentapprmngMapper.confirmApplypayment(settlementDto);
    }

    public int deleteCustomer(CustomerDto customerDto) {
        return applypaymentapprmngMapper.deleteCustomer(customerDto);
    }
}
