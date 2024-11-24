package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RefundMapper {

    List<CamelCaseMap> findApplypayment(SettlementDto settlementDto);
    int saveApplypayment(SettlementDto settlementDto);

    int deleteCustomer(CustomerDto customerDto);
}
