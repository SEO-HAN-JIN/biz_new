package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpAgencyDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpAgencyService;
import com.biz.framework.service.pages.EmpCustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/emp_agency")
@RequiredArgsConstructor
public class EmpAgencyRestController {

    private final EmpAgencyService empCustomerService;

    @GetMapping("/emp/list")
    public List<CamelCaseMap> findEmpList(EmpAgencyDto empAgencyDto) {
        return empCustomerService.findEmplList(empAgencyDto);
    }

    @GetMapping("/agency/list")
    public List<CamelCaseMap> findEmpAgencyList(EmpAgencyDto empAgencyDto) {
        return empCustomerService.findEmpAgencyList(empAgencyDto);
    }

    @GetMapping("/agency/modal/list")
    public List<CamelCaseMap> findAgencyList(EmpAgencyDto empAgencyDto) {
        return empCustomerService.findAgencyList(empAgencyDto);
    }

    @PostMapping("/save")
    public int saveEmpAgencyList(@RequestBody List<EmpAgencyDto> empAgencyDtoList) {
        return empCustomerService.saveEmpAgencyList(empAgencyDtoList);
    }

    @DeleteMapping
    public int deleteEmpAgencyList(@RequestBody List<EmpAgencyDto> empAgencyDtoList) {
        return empCustomerService.deleteEmpAgencyList(empAgencyDtoList);
    }
}
