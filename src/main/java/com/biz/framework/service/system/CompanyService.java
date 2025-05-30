package com.biz.framework.service.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CompanyDto;
import com.biz.framework.mapper.system.CompanyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CompanyService {

    private final CompanyMapper companyMapper;

    public List<CompanyDto> findCompanyList() {
        return companyMapper.findCompanyList();
    }

    public CamelCaseMap findCompany(CompanyDto companyDto) {
        return companyMapper.findCompany(companyDto);
    }

    public int updateCompany(CompanyDto companyDto) {
        return companyMapper.updateCompany(companyDto);
    }
}
