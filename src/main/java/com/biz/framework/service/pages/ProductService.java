package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.ProductMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
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
                    throw new ServiceException("동일한 상품코드가 존재합니다.");
                }
                String prodId = productMapper.createProdId(productDto);
                productDto.setProdId(prodId);

                result += productMapper.saveProducts(productDto);

                if (!CollectionUtils.isEmpty(productDto.getProductModalGridDtoList())) {
                    for (ProductDto.ProductItemDto productModalGridDto : productDto.getProductModalGridDtoList()) {
                        productModalGridDto.setProdId(prodId);
                        productMapper.saveProdItems(productModalGridDto);
                    }
                }
            }
            case U -> {
                result += productMapper.saveProducts(productDto);

                if (!CollectionUtils.isEmpty(productDto.getProductModalGridDtoList())) {
                    productMapper.deleteProductItemAll(productDto);
                    for (ProductDto.ProductItemDto productModalGridDto : productDto.getProductModalGridDtoList()) {
                        productMapper.saveProdItems(productModalGridDto);
                    }

                }
            }
        }

        return result;
    }

    public int deleteProduct(ProductDto productDto) {
        int result = 0;
        result += productMapper.deleteProducts(productDto);
        productMapper.deleteProductItemAll(productDto);
        return result;
    }

    public CamelCaseMap findProductsInfo(ProductDto productDto) {
        return productMapper.findProductsInfo(productDto);
    }

    public List<CamelCaseMap> findProductPopList(ProductDto productDto) {
        return productMapper.findProductPopList(productDto);
    }

    public List<CamelCaseMap> findProductItemList(ProductDto productDto) {
        return productMapper.findProductItemList(productDto);
    }

    public int deleteProductItem(String prodId, Integer seq) {
        AuthenticationDto authenticationDto = (AuthenticationDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String coCode = authenticationDto.getCoCode();

        return productMapper.deleteProductItem(coCode, prodId, seq);
    }
}
