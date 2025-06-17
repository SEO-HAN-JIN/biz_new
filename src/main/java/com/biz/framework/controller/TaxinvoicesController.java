package com.biz.framework.controller;

import com.biz.framework.dto.pages.TaxinvoicesDto;
import com.biz.framework.service.pages.TaxinvoicesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pages/taxinvoices")
@RequiredArgsConstructor
public class TaxinvoicesController {

    private final TaxinvoicesService taxinvoicesService;

    /**
     * 발행 POST JSON
     */
    @PostMapping(path = "/form", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String formMultiPage(
            @RequestBody TaxinvoicesDto dtoWrapper,
            Model model
    ) {
        // 각 번호별 DTO 생성
        TaxinvoicesDto form = taxinvoicesService.loadFormList(dtoWrapper);
        model.addAttribute("form", form);
        return "pages/popup/invoiceFormModal :: invoiceFormContent";
    }

    /**
    * 세금계산서 조회 URL
    */
    @GetMapping("/popup")
    public ResponseEntity<String> getPopupUrl(TaxinvoicesDto dto)
    {
        String url = taxinvoicesService.getTaxinvoicePopupUrl(dto);
        return ResponseEntity.ok(url);
    }

}
