package com.biz.framework.restcontroller.system;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CompanyDto;
import com.biz.framework.service.system.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system/company")
@RequiredArgsConstructor
public class CompanyRestController {

    private final CompanyService companyService;

    @GetMapping("/list")
    public List<CompanyDto> findComapnyList() {
        return companyService.findCompanyList();
    }

    @GetMapping
    public CamelCaseMap findCompany(CompanyDto companyDto) {
        return companyService.findCompany(companyDto);
    }

    @PostMapping("/upd")
    public int updateCompany(@RequestBody CompanyDto companyDto) {
        return companyService.updateCompany(companyDto);
    }
}
