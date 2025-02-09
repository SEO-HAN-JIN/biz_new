package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.service.pages.AgencyProductService;
import com.biz.framework.service.pages.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/agency_product")
@RequiredArgsConstructor
public class AgencyProductRestController {

    private final AgencyProductService agencyProductService;


    @GetMapping
    public List<CamelCaseMap> findProduct(ProductDto productDto) {
        return agencyProductService.findProducts(productDto);
    }

    @PostMapping
    public int saveProduct(@RequestBody ProductDto productDto) {
        return agencyProductService.saveProduct(productDto);
    }

    @DeleteMapping
    public int deleteProduct(@RequestBody ProductDto productDto) {
        return agencyProductService.deleteProduct(productDto);
    }

    @GetMapping("/pop/list")
    public List<CamelCaseMap> findProductPopList(ProductDto productDto) {
        return agencyProductService.findProductPopList(productDto);
    }
}
