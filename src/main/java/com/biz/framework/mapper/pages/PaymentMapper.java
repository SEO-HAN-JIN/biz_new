package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PaymentMapper {

    List<CamelCaseMap> findPaymentList(SettlementmstDto settlementmstDto);

    List<CamelCaseMap> findPaymentInfoList(SettlementmstDto settlementmstDto);

    List<CamelCaseMap> findPaymentEtcList(SettlementmstDto settlementmstDto);

    String createPaySeq(SettlementmstDto settlementmstDto);

    int savePaymentInfo(SettlementmstDto settlementmstDto);

    int removePaymentInfo(SettlementmstDto settlementmstDto);

    int removePaymentInfoAll(SettlementmstDto settlementmstDto);
}
