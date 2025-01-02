package com.biz.framework.service.pages;

import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MileageHisService {

    private final MileageHisMapper mileageHisMapper;

    public int saveMileageHis(MileageHisDto mileageHisDto) {
        return mileageHisMapper.saveMileageHis(mileageHisDto);
    }

}
