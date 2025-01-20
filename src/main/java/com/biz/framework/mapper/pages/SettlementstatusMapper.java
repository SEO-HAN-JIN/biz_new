package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SettlementstatusMapper {

    List<CamelCaseMap> findSettlementstatus(SettlementmstDto settlementmstDto);
    CamelCaseMap findSettlementstatusInfo(SettlementmstDto settlementmstDto);
}
