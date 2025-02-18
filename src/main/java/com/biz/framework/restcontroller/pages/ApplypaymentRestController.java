package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.CustomerService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.pages.ProductService;
import com.biz.framework.service.system.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/applypayment")
@RequiredArgsConstructor
public class ApplypaymentRestController {

    private final EmpService empService;
    private final ApplypaymentService applypaymentService;
    private final CustomerService customerService;
    private final ProductService productService;

    @GetMapping("/find/incentiveRate")
    public double findIncentiveRate(SettlementDto settlementDto) {
        return applypaymentService.findIncentiveRate(settlementDto);
    }

    @GetMapping
    public List<CamelCaseMap> findCustomers(SettlementDto settlementDto) {
        return applypaymentService.findApplypayment(settlementDto);
    }

    @PostMapping
    public int saveCustomer(@RequestBody SettlementDto settlementDto) {
        settlementDto.setReqGubun("AQ");        // 요청구분(SE01) : AQ(정산요청)
        settlementDto.setApplyStatus("01");     // 승인상태(SE04) : 01(승인요청)
        return applypaymentService.saveApplypayment(settlementDto);
    }

    @DeleteMapping
    public int deleteSettlement(@RequestBody SettlementDto settlementDto) {
        return applypaymentService.deleteSettlement(settlementDto);
    }

    @GetMapping("/custinfo")
    public CamelCaseMap searchCustomer(CustomerDto customerDto) {
        return customerService.findCustomerInfo(customerDto);
    }

    @GetMapping("/prodinfo")
    public CamelCaseMap searchProducts(ProductDto productDto) {
        return productService.findProductsInfo(productDto);
    }

    @PostMapping("/cancel")
    public int cancelSettlement(@RequestBody SettlementDto settlementDto) {
        return applypaymentService.cancelSettlement(settlementDto);
    }
}
