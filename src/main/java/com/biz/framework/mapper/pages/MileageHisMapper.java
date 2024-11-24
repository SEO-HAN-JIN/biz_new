package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MileageHisMapper {

    int saveMileageHis(MileageHisDto mileageHisDto);

    int deleteMileageHisByBizNoEmpId(MileageHisDto mileageHisDto);

    int addMileageHistory(MileageHisDto mileageHisDto);
}
