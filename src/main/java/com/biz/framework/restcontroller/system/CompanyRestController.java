package com.biz.framework.restcontroller.system;

import com.biz.framework.dto.system.CompanyDto;
import com.biz.framework.service.system.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
