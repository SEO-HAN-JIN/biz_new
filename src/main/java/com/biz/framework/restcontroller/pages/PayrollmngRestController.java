package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import com.biz.framework.service.pages.PayrollmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/payrollmng")
@RequiredArgsConstructor
public class PayrollmngRestController {

    private final PayrollmngService payrollmngService;

    @GetMapping
    public List<CamelCaseMap> findPayrollList(SettlementDto settlementDto) {
        return payrollmngService.findPayrollList(settlementDto);
    }

    /* 요청 승인 */
    @PostMapping("/payroll")
    public int payrollApplypayment(@RequestBody SettlementDto settlementDto) {
        return payrollmngService.payrollApplypayment(settlementDto);
    }
//
//    @PostMapping("/cancel")
//    public int cancelApplypayment(@RequestBody SettlementDto settlementDto) {
//        return payrollmngService.cancelApplypayment(settlementDto);
//    }
}
