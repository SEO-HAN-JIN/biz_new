package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyProductDto;
import com.biz.framework.service.pages.AgencyProductRelService;
import com.biz.framework.service.pages.EmpAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/agency_product_rel")
@RequiredArgsConstructor
public class AgencyProductRelRestController {

    private final AgencyProductRelService agencyProductRelService;

    @GetMapping("/agency/list")
    public List<CamelCaseMap> findAgencyList(AgencyProductDto agencyProductDto) {
        return agencyProductRelService.findAgencyList(agencyProductDto);
    }

    @GetMapping("/product/list")
    public List<CamelCaseMap> findAgencyProductList(AgencyProductDto agencyProductDto) {
        return agencyProductRelService.findAgencyProductList(agencyProductDto);
    }

    @GetMapping("/product/modal/list")
    public List<CamelCaseMap> findAgProductList(AgencyProductDto agencyProductDto) {
        return agencyProductRelService.findAgProductList(agencyProductDto);
    }

    @PostMapping("/save")
    public int saveEmpAgencyList(@RequestBody List<AgencyProductDto> agencyProductDtoList) {
        return agencyProductRelService.saveEmpAgencyList(agencyProductDtoList);
    }

    @DeleteMapping
    public int deleteEmpAgencyList(@RequestBody List<AgencyProductDto> agencyProductDtoList) {
        return agencyProductRelService.deleteEmpAgencyList(agencyProductDtoList);
    }
}
