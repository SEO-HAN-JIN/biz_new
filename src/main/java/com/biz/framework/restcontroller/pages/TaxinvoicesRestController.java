package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.dto.pages.TaxinvoicesDto;
import com.biz.framework.service.pages.PaymentService;
import com.biz.framework.service.pages.TaxinvoicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/taxinvoices")
@RequiredArgsConstructor
public class TaxinvoicesRestController {

    private final TaxinvoicesService taxinvoicesService;

    @GetMapping
    public List<CamelCaseMap> findTaxinvoicesList(TaxinvoicesDto taxinvoicesDto) {
        return taxinvoicesService.findTaxinvoicesList(taxinvoicesDto);
    }

    @PostMapping
    public int createInvoices(@RequestBody TaxinvoicesDto taxinvoicesDto) {
        return taxinvoicesService.createInvoices(taxinvoicesDto);
    }


}
