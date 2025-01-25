package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpAgencyDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface EmpAgencyMapper {

    List<CamelCaseMap> findEmplList(EmpAgencyDto empAgencyDto);

    List<CamelCaseMap> findEmpAgencyList(EmpAgencyDto empAgencyDto);

    int saveEmpAgencyList(EmpAgencyDto empAgencyDto);

    int checkDupl(EmpAgencyDto empAgencyDto);

    int deleteEmpAgencyList(EmpAgencyDto empAgencyDto);

    List<CamelCaseMap> findAgencyList(EmpAgencyDto empAgencyDto);
}
