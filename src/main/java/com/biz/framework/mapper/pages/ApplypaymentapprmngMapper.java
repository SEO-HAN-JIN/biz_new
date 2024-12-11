package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplypaymentapprmngMapper {

    List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto);

    String createConfirmSeq(SettlementmstDto settlementmstDto);

    int confirmApplypayment(SettlementmstDto settlementmstDto);

    int checkPayrollInd(SettlementmstDto settlementmstDto);

    int cancelApplypayment(SettlementmstDto settlementmstDto);
}
