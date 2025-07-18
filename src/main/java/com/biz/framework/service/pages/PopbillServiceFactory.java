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

    public TaxinvoiceService getService(CompanyDto companyDto) {

        TaxinvoiceServiceImp service = new TaxinvoiceServiceImp();
        service.setLinkID(companyDto.getTaxLinkId());
        service.setSecretKey(companyDto.getTaxSecretKey());
        service.setTest("Y".equals(companyDto.getTaxTestInd()));
        service.setUseStaticIP(false);

        return service;
    }
}
