package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyDto;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpDto;
import com.biz.framework.service.pages.AgencyService;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/agency")
@RequiredArgsConstructor
public class AgencyRestController {

    private final AgencyService agencyService;

    @GetMapping
    public List<CamelCaseMap> findAgency(AgencyDto agencyDto) {
        return agencyService.findAgency(agencyDto);
    }

    @PostMapping
    public int saveAgency(@RequestBody AgencyDto agencyDto) {
        return agencyService.saveAgency(agencyDto);
    }

    @DeleteMapping
    public int deleteAgency(@RequestBody AgencyDto agencyDto) {
        return agencyService.deleteAgency(agencyDto);
    }

    @GetMapping("/list")
    public List<CamelCaseMap> findAgencyList(AgencyDto agencyDto) {
        return agencyService.findAgencyList(agencyDto);
    }
}
