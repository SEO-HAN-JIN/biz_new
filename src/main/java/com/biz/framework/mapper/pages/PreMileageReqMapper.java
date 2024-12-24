package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageReqDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PreMileageReqMapper {

    List<CamelCaseMap> findPreMileageReq(MileageReqDto mileageReqDto);
    int saveMileageReq(MileageReqDto mileageReqDto);
    int updateMileageReq(MileageReqDto mileageReqDto);
    int deletePreMileageReq(MileageReqDto mileageReqDto);

    int checkApproveInd(MileageReqDto mileageReqDto);

    int checkAlreadyApprov(MileageReqDto mileageReqDto);
}
