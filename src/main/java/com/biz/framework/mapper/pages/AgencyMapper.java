package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyDto;
import com.biz.framework.dto.pages.CustomerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AgencyMapper {

    List<CamelCaseMap> findAgency(AgencyDto agencyDto);

    int saveAgency(AgencyDto agencyDto);

    int deleteAgency(AgencyDto agencyDto);

    List<CamelCaseMap> findAgencyList(AgencyDto agencyDto);

    int updateAgency(AgencyDto agencyDto);
}
