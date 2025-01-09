package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApplypaymentMapper {

    double findIncentiveRate(SettlementDto settlementDto);

    List<CamelCaseMap> findApplypayment(SettlementDto settlementDto);

    int saveApplypayment(SettlementDto settlementDto);

    int deleteSettlement(SettlementDto settlementDto);

    List<CamelCaseMap> findSettlement(SettlementDto settlementDto);

    String checkApplyStatus(SettlementDto settlementDto);

    int updateApplypayment(SettlementDto settlementDto);

    String createSettlementSeq(SettlementDto settlementDto);

    int cancelSettlement(SettlementDto dto);
}
