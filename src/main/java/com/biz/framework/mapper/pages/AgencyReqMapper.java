package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyReqDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgencyReqMapper {

    double findIncentiveRate(AgencyReqDto agencyReqDto);

    List<CamelCaseMap> findApplypayment(AgencyReqDto agencyReqDto);

    int saveAgencyReq(AgencyReqDto agencyReqDto);

    int deleteSettlement(AgencyReqDto agencyReqDto);

    List<CamelCaseMap> findSettlement(AgencyReqDto agencyReqDto);

    String checkApplyStatus(AgencyReqDto agencyReqDto);

    int updateAgencyReq(AgencyReqDto agencyReqDto);

    String createAgencySeq(AgencyReqDto agencyReqDto);

    int cancelSettlement(AgencyReqDto dto);
}
