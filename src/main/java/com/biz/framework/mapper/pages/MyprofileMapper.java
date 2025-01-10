package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MyprofileMapper {

    CamelCaseMap findMyprofile(EmpDto empDto);
    int saveEmp(EmpDto empDto);
    int deleteEmp(EmpDto empDto);
    int checkDuplEmpId(EmpDto empDto);

    List<CamelCaseMap> findAllEmps(EmpDto empDto);
}
