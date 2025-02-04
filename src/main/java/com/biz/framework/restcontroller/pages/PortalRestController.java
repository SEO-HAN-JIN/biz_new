package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.security.dto.AuthenticationDto;
import com.biz.framework.service.pages.PortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/approval")
@RequiredArgsConstructor
public class PortalRestController {
    private final PortalService portalService;

    @GetMapping("/data")
    public List<CamelCaseMap> getApprovalData(SettlementmstDto settlementmstDto) {
        return portalService.getApprovalData(settlementmstDto);
    }

    @GetMapping("/settlement")
    public List<CamelCaseMap> getSettlementData(SettlementmstDto settlementmstDto) {
        AuthenticationDto authentication = (AuthenticationDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String coCode = authentication.getCoCode();

        settlementmstDto.setLoginCoId(coCode);
        return portalService.getSettlementData(settlementmstDto);
    }

    @GetMapping("/monthly/pay")
    public List<CamelCaseMap> getMonthlyPayData() {
        SettlementmstDto settlementmstDto = new SettlementmstDto();

        AuthenticationDto authenticationDto = (AuthenticationDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = authenticationDto.getUserId();
        settlementmstDto.setUserId(userId);

        return portalService.getMonthlyPayData(settlementmstDto);
    }
}
