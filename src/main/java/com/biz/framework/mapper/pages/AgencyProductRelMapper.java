package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyProductRelMapper {

    List<CamelCaseMap> findAgencyList(AgencyProductDto agencyProductDto);

    List<CamelCaseMap> findAgencyProductList(AgencyProductDto agencyProductDto);

    int saveEmpAgencyList(AgencyProductDto agencyProductDto);

    int checkDupl(AgencyProductDto agencyProductDto);

    int deleteEmpAgencyList(AgencyProductDto agencyProductDto);

    List<CamelCaseMap> findAgProductList(AgencyProductDto agencyProductDto);
}
