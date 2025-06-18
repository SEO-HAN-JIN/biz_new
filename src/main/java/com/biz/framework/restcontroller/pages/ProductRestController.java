package com.biz.framework.restcontroller.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.service.pages.ApplypaymentService;
import com.biz.framework.service.pages.EmpService;
import com.biz.framework.service.pages.ProductService;
import com.biz.framework.service.system.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pages/product")
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;


    @GetMapping
    public List<CamelCaseMap> findProduct(ProductDto productDto) {
        return productService.findProducts(productDto);
    }

    @PostMapping
    public int saveProduct(@RequestBody ProductDto productDto) {
        return productService.saveProduct(productDto);
    }

    @DeleteMapping
    public int deleteProduct(@RequestBody ProductDto productDto) {
        return productService.deleteProduct(productDto);
    }

    @GetMapping("/pop/list")
    public List<CamelCaseMap> findProductPopList(ProductDto productDto) {
        return productService.findProductPopList(productDto);
    }

    @GetMapping("/item/list")
    public List<CamelCaseMap> findProductItemList(ProductDto productDto) {
        return productService.findProductItemList(productDto);
    }

    @DeleteMapping("/delete/item/{prodId}/{seq}")
    public int deleteProductItem(@PathVariable String prodId, @PathVariable Integer seq) {
        return productService.deleteProductItem(prodId, seq);
    }
}
