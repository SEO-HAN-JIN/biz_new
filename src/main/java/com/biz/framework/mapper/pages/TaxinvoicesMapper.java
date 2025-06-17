package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.dto.pages.TaxinvoicesDto;
import com.biz.framework.dto.pages.TaxinvoiceslineDto;
import com.biz.framework.dto.system.CompanyDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaxinvoicesMapper {

    List<CamelCaseMap> findTaxinvoicesList(TaxinvoicesDto taxinvoicesDto);

    TaxinvoicesDto findCustInfo(TaxinvoicesDto taxinvoicesDto);
    TaxinvoicesDto findTaxinvoicesDetail(TaxinvoicesDto taxinvoicesDto);

    CompanyDto findCompanyInfo(TaxinvoicesDto taxinvoicesDto);

    int updateTaxinvoice(TaxinvoicesDto map);
    int updateSettlement(Map<String,Object> map);
    int cancelTaxinvoice(TaxinvoicesDto map);

    String createTaxkey(TaxinvoicesDto taxinvoicesDto);
    int insertTaxinvoice(TaxinvoicesDto taxinvoicesDto);
    int insertTaxinvoiceline(TaxinvoiceslineDto taxinvoiceslineDto);

}
