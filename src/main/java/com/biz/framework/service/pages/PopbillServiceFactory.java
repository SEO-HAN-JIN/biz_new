package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.CompanyDto;
import com.biz.framework.mapper.system.CompanyMapper;
import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.TaxinvoiceServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PopbillServiceFactory {

    private final CompanyMapper companyMapper;

    public TaxinvoiceService getService(String coCode) {

        CompanyDto companyDto = new CompanyDto();
        companyDto.setCoCode(coCode);

        CamelCaseMap findCompany = companyMapper.findCompany(companyDto);

        TaxinvoiceServiceImp service = new TaxinvoiceServiceImp();
        service.setLinkID((String) findCompany.get("taxLinkId"));
        service.setSecretKey((String) findCompany.get("taxSecretKey"));
        service.setTest("Y".equals(findCompany.get("taxTestInd")));
        service.setUseStaticIP(false);

        return service;
    }
}
