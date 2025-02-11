package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.service.pages.ApplypaymentapprmngService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/agencyapplymng")
@RequiredArgsConstructor
public class AgencyapplymngRestController {

    private final ApplypaymentapprmngService applypaymentapprmngService;

    @GetMapping
    public List<CamelCaseMap> findApplypaymentmngList(SettlementDto settlementDto) {
        return applypaymentapprmngService.findApplypaymentmngList(settlementDto);
    }

    /* 요청 승인 */
    @PostMapping
    public int confirmApplypayment(@RequestBody SettlementmstDto settlementmstDto) {
        return applypaymentapprmngService.confirmApplypayment(settlementmstDto);
    }

    @PostMapping("/cancel")
    public int cancelApplypayment(@RequestBody SettlementmstDto settlementmstDto) {
        return applypaymentapprmngService.cancelApplypayment(settlementmstDto);
    }
}
