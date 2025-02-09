package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.ProductDto;
import com.biz.framework.mapper.pages.AgencyProductMapper;
import com.biz.framework.mapper.pages.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyProductService {

    private final AgencyProductMapper agencyProductMapper;

    public List<CamelCaseMap> findProducts(ProductDto productDto) {
        return agencyProductMapper.findProducts(productDto);
    }

    public int saveProduct(ProductDto productDto) {
        int result = 0;
        switch (productDto.getRowStatus()) {
            case C -> {
                if (agencyProductMapper.checkDuplProduct(productDto) > 0) {
                    throw new ServiceException("동일한 상품코드가 존재합니다.");
                }
                if (StringUtils.isEmpty(productDto.getProdId())) {
                    productDto.setProdId(agencyProductMapper.createProdId(productDto));
                }
                result += agencyProductMapper.saveProducts(productDto);
            }
            case U -> result += agencyProductMapper.saveProducts(productDto);
        }

        return result;
    }

    public int deleteProduct(ProductDto productDto) {
        return agencyProductMapper.deleteProducts(productDto);
    }

    public CamelCaseMap findProductsInfo(ProductDto productDto) {
        return agencyProductMapper.findProductsInfo(productDto);
    }

    public List<CamelCaseMap> findProductPopList(ProductDto productDto) {
        return agencyProductMapper.findProductPopList(productDto);
    }
}
