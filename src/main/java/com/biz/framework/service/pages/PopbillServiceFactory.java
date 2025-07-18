package com.biz.framework.service.pages;

import com.popbill.api.TaxinvoiceService;
import com.popbill.api.taxinvoice.TaxinvoiceServiceImp;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PopbillServiceFactory {

    private final Map<String, TaxinvoiceService> serviceMap = new HashMap<>();

    public TaxinvoiceService getService(String corpNum, String linkId, String secretKey) {
        if (serviceMap.containsKey(corpNum)) {
            return serviceMap.get(corpNum);
        }

        TaxinvoiceServiceImp service = new TaxinvoiceServiceImp();
        service.setLinkID(linkId);       // 동적으로 설정
        service.setSecretKey(secretKey); // 동적으로 설정
        service.setTest(true);
        service.setUseStaticIP(false);

        serviceMap.put(corpNum, service);
        return service;
    }
}
