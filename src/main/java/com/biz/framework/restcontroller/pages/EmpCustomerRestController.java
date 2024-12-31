package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpCustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.ProgramService;
import com.biz.framework.service.system.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/emp_customer")
@RequiredArgsConstructor
public class EmpCustomerRestController {

    private final RoleService roleService;
    private final EmpService empService;
    private final CustomerService customerService;
    private final EmpCustomerService empCustomerService;

    @GetMapping("/emp/list")
    public List<CamelCaseMap> findEmpList(EmpCustomerDto empCustomerDto) {
        return empCustomerService.findEmplList(empCustomerDto);
    }

    @GetMapping("/customer/list")
    public List<CamelCaseMap> findCustoemrList(EmpCustomerDto empCustomerDto) {
        return empCustomerService.findCustoemrList(empCustomerDto);
    }

    @GetMapping("/customer/modal/list")
    public List<CamelCaseMap> findCustomerList(EmpCustomerDto empCustomerDto) {
        return empCustomerService.findCustomerListModal(empCustomerDto);
    }

    @PostMapping("/save")
    public int saveEmpCustomerList(@RequestBody List<EmpCustomerDto> empCustomerDtoList) {
        return empCustomerService.saveEmpCustomerList(empCustomerDtoList);
    }

    @DeleteMapping
    public int deleteEmpCustomerList(@RequestBody List<EmpCustomerDto> empCustomerDtoList) {
        return empCustomerService.deleteEmpCustomerList(empCustomerDtoList);
    }
}
