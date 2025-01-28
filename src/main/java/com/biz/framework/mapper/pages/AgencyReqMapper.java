package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyReqMapper {

    double findIncentiveRate(AgencyReqDto agencyReqDto);

    List<CamelCaseMap> findApplypayment(AgencyReqDto agencyReqDto);

    int saveApplypayment(AgencyReqDto agencyReqDto);

    int deleteSettlement(AgencyReqDto agencyReqDto);

    List<CamelCaseMap> findSettlement(AgencyReqDto agencyReqDto);

    String checkApplyStatus(AgencyReqDto agencyReqDto);

    int updateApplypayment(AgencyReqDto agencyReqDto);

    String createSettlementSeq(AgencyReqDto agencyReqDto);

    int cancelSettlement(AgencyReqDto dto);
}
