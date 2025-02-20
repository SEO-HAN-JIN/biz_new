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

    int confirmApplypaymentmst(SettlementmstDto settlementmstDto);

    int updateSettlement(SettlementDto settlementDto);

    int checkPayrollInd(SettlementmstDto settlementmstDto);

    int checkSettlementmst(SettlementmstDto settlementmstDto);

    int cancelApplypayment(SettlementmstDto settlementmstDto);

    SettlementDto findSettlement(SettlementmstDto settlementmstDto);

    SettlementmstDto findSettlementmst(SettlementmstDto settlementmstDto);

    int deleteSettlementmst(SettlementmstDto settlementmstDto);
    int deleteSettlement(SettlementDto settlementDto);
}
