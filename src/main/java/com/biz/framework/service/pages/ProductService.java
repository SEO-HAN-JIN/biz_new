package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ProductService {

    private final ProductMapper productMapper;

    public List<CamelCaseMap> findProducts(ProductDto productDto) {
        return productMapper.findProducts(productDto);
    }

    public int saveProduct(ProductDto productDto) {
        int result = 0;
        switch (productDto.getRowStatus()) {
            case C -> {
                if (productMapper.checkDuplProduct(productDto) > 0) {
                    productMapper.updateProductEffDate(productDto);
                }
                if (StringUtils.isEmpty(productDto.getProdId())) {
                    productDto.setProdId(productMapper.createProdId(productDto));
                }
                result += productMapper.saveProducts(productDto);
            }
            case U -> result += productMapper.saveProducts(productDto);
        }

        return result;
    }

    public int deleteProduct(ProductDto productDto) {
        return productMapper.deleteProducts(productDto);
    }

    public CamelCaseMap findProductsInfo(ProductDto productDto) {
        return productMapper.findProductsInfo(productDto);
    }
}
