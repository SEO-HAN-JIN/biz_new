package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PortalMapper {
    List<CamelCaseMap> getApprovalData();
    List<CamelCaseMap> getSettlementData(SettlementmstDto settlementmstDto);
    List<CamelCaseMap> getMonthlyPayData(SettlementmstDto settlementmstDto);
}
