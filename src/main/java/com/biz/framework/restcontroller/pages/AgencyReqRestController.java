package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.*;
import com.biz.framework.service.pages.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/agencyreq")
@RequiredArgsConstructor
public class AgencyReqRestController {

    private final AgencyReqService agencyReqService;
    private final AgencyService agencyService;
    private final AgencyProductService agencyProductService;
    private final AgencyProductRelService agencyProductRelService;


    @GetMapping("/find/incentiveRate")
    public CamelCaseMap findIncentiveRate(AgencyDto agencyDto) {
        return agencyService.findAgencyIncentiveRate(agencyDto);
    }

    @GetMapping("/find/agency/product/list")
    public List<CamelCaseMap> findProdList(AgencyProductDto agencyProductDto) {
        return agencyProductRelService.findAgencyProductList(agencyProductDto);
    }

    @GetMapping("/find/prodAmt")
    public CamelCaseMap findProdAmt(AgencyProductDto agencyProductDto) {
        return agencyProductService.findProdAmt(agencyProductDto);
    }

    @GetMapping("/find/product/list")
    public List<CamelCaseMap> findProductList(AgencyProductDto agencyProductDto) {
        return agencyProductRelService.findAgProductList(agencyProductDto);
    }

    @GetMapping
    public List<CamelCaseMap> findCustomers(AgencyReqDto agencyReqDto) {
        return agencyReqService.findApplypayment(agencyReqDto);
    }

    @PostMapping
    public int saveCustomer(@RequestBody AgencyReqDto agencyReqDto) {
        return agencyReqService.saveApplypayment(agencyReqDto);
    }

    @DeleteMapping
    public int deleteSettlement(@RequestBody AgencyReqDto agencyReqDto) {
        return agencyReqService.deleteSettlement(agencyReqDto);
    }

    @PostMapping("/cancel")
    public int cancelSettlement(@RequestBody AgencyReqDto agencyReqDto) {
        return agencyReqService.cancelSettlement(agencyReqDto);
    }

}
