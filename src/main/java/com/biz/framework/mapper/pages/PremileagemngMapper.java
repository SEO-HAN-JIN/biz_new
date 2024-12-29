package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.dto.pages.SettlementDto;
import com.biz.framework.dto.pages.SettlementmstDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PremileagemngMapper {

    List<CamelCaseMap> findPremileagemngList(MileageReqDto mileageReqDto);

    int cancelApplypayment(SettlementmstDto settlementmstDto);

    SettlementDto findSettlement(SettlementmstDto settlementmstDto);

    String findApplyStatus(MileageReqDto mileageReqDto);

    int updateApplyStatus(MileageReqDto mileageReqDto);
}
