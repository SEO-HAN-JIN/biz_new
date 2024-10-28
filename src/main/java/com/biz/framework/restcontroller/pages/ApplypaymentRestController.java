package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/applypayment")
@RequiredArgsConstructor
public class ApplypaymentRestController {

    private final UserService userService;
    private final EmpService empService;
    private final ApplypaymentService applypaymentService;

    @GetMapping("/emps")
    public List<CamelCaseMap> findEmps() {
        return empService.findAllEmps();
    }

    @GetMapping
    public List<CamelCaseMap> findCustomers(SettlementDto settlementDto) {
        return applypaymentService.findApplypayment(settlementDto);
    }

    @PostMapping
    public int saveCustomer(@RequestBody SettlementDto settlementDto) {
        return applypaymentService.saveApplypayment(settlementDto);
    }

    @DeleteMapping
    public int deleteCustomer(@RequestBody CustomerDto customerDto) {
        return applypaymentService.deleteCustomer(customerDto);
    }
}
