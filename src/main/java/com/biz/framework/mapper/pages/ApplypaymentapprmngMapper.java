package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplypaymentapprmngMapper {

    List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto);
    int confirmApplypayment(SettlementDto settlementDto);

    int checkPayrollInd(SettlementDto settlementDto);

    int cancelApplypayment(SettlementDto settlementDto);
}
