package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import com.biz.framework.mapper.pages.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentMapper paymentMapper;

    public List<CamelCaseMap> findPaymentList(SettlementmstDto settlementmstDto) {
        return paymentMapper.findPaymentList(settlementmstDto);
    }

    public List<CamelCaseMap> findPaymentInfoList(SettlementmstDto settlementmstDto) {
        return paymentMapper.findPaymentInfoList(settlementmstDto);
    }

    public List<CamelCaseMap> findPaymentEtcList(SettlementmstDto settlementmstDto) {
        return paymentMapper.findPaymentEtcList(settlementmstDto);
    }

    public int savePayment(List<SettlementmstDto> settlementmstDto)
    {
        int result = 0;
        if(!CollectionUtils.isEmpty(settlementmstDto))
        {
            for(SettlementmstDto dto : settlementmstDto)
            {
                if(StringUtils.isEmpty(dto.getPaySeq()))
                    dto.setPaySeq(paymentMapper.createPaySeq(dto));

                result += paymentMapper.savePaymentInfo(dto);
            }
        }
        return result;
    }

    public int removePayment(List<SettlementmstDto> settlementmstDto)
    {
        int result = 0;
        if(!CollectionUtils.isEmpty(settlementmstDto))
        {
            for(SettlementmstDto dto : settlementmstDto)
            {
                result += paymentMapper.removePaymentInfo(dto);
            }
        }
        return result;
    }
}
