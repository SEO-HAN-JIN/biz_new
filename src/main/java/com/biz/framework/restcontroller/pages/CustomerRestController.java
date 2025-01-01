package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/customer")
@RequiredArgsConstructor
public class CustomerRestController {

    private final UserService userService;
    private final EmpService empService;
    private final CustomerService customerService;

    @GetMapping("/emps")
    public List<CamelCaseMap> findEmps(EmpDto empDto) {
        return empService.findAllEmps(empDto);
    }

    @GetMapping
    public List<CamelCaseMap> findCustomers(CustomerDto customerDto) {
        return customerService.findCustomers(customerDto);
    }

    @PostMapping
    public int saveCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.saveCustomer(customerDto);
    }

    @DeleteMapping
    public int deleteCustomer(@RequestBody CustomerDto customerDto) {
        return customerService.deleteCustomer(customerDto);
    }
}
