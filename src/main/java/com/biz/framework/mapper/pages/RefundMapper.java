package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.SettlementDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RefundMapper {
    List<CamelCaseMap> findRefund(SettlementDto settlementDto);
    List<CamelCaseMap> findSettlement(SettlementDto settlementDto);
    List<SettlementDto.TbSettlementProdItemDto> findProductItemListBySettlementSeqAndProdId(SettlementDto.TbSettlementProdItemDto tbSettlementProdItemDto);
    List<CamelCaseMap> findRefundItems(SettlementDto settlementDto);
    List<CamelCaseMap> findRefundItemsBySettlementSeq(SettlementDto.TbSettlementRefundItemDto tbSettlementRefundItemDto);
}
