package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyProductDto;
import com.biz.framework.dto.pages.ProductDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyProductMapper {

    List<CamelCaseMap> findProducts(ProductDto productDto);
    CamelCaseMap findProductsInfo(ProductDto productDto);

    int saveProducts(ProductDto productDto);
    int deleteProducts(ProductDto productDto);

    int checkDuplProduct(ProductDto productDto);

    String createProdId(ProductDto productDto);

    List<CamelCaseMap> findProductPopList(ProductDto productDto);

    CamelCaseMap findProdAmtByPk(AgencyProductDto agencyProductDto);
}
