package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.dto.pages.TaxinvoicesDto;
import com.biz.framework.dto.pages.TaxinvoiceslineDto;
import com.biz.framework.mapper.pages.TaxinvoicesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaxinvoicePreparer {

    private final TaxinvoicesMapper mapper;

    @Autowired
    public TaxinvoicePreparer(TaxinvoicesMapper mapper) {
        this.mapper = mapper;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateTaxinvoiceBefore(TaxinvoicesDto form) {
        String taxKey = mapper.createTaxkey(form);
        form.setTaxKey(taxKey);

        if (mapper.insertTaxinvoice(form) <= 0)
            throw new ServiceException("세금계산서 사전 작업 도중 오류가 발생했습니다.");

        short seq = 1;
        for (TaxinvoiceslineDto line : form.getTaxinvoiceslineDtoList()) {
            line.setNo((int) seq++);
            line.setTaxKey(taxKey);
            mapper.insertTaxinvoiceline(line);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelTaxinvoice(TaxinvoicesDto form)
    {
        mapper.cancelTaxinvoice(form);
    }
}
