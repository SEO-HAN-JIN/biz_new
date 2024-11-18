package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/applypaymentapprmng")
@RequiredArgsConstructor
public class ApplypaymentapprmngRestController {

    private final ApplypaymentapprmngService applypaymentapprmngService;

    @GetMapping
    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngService.findApplypaymentmngList(settlementDto);
    }

    @PostMapping
    public int confirmApplypayment(@RequestBody SettlementDto settlementDto) {
        return applypaymentapprmngService.confirmApplypayment(settlementDto);
    }

    @DeleteMapping
    public int deleteCustomer(@RequestBody CustomerDto customerDto) {
        return applypaymentapprmngService.deleteCustomer(customerDto);
    }
}
