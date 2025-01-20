package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.pages.SettlementstatusService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pages/settlementstatus")
@RequiredArgsConstructor
public class SettlementstatusRestController {

    private final SettlementstatusService settlementstatusService;

    @GetMapping
    public Map findSettlementstatus(SettlementmstDto settlementmstDto) {
        return settlementstatusService.findSettlementstatus(settlementmstDto);
    }
}
