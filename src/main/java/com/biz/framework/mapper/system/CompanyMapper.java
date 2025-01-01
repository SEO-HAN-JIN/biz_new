package com.biz.framework.mapper.system;

import com.biz.framework.dto.system.CompanyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompanyMapper {
    List<CompanyDto> findCompanyList();
}