package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.dto.pages.MileageReqDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.PreMileageReqMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PreMileageReqService {

    private final PreMileageReqMapper preMileageReqMapper;

    public List<CamelCaseMap> findPreMileageReq(MileageReqDto mileageReqDto) {
        return preMileageReqMapper.findPreMileageReq(mileageReqDto);
    }

    public int savePreMileageReq(MileageReqDto mileageReqDto) {
        int result = 0;

        switch (mileageReqDto.getRowStatus()) {
            case C -> result += preMileageReqMapper.saveMileageReq(mileageReqDto);
            case U -> {
                if (preMileageReqMapper.checkApproveInd(mileageReqDto) > 0 ) {
                    throw new ServiceException("이미 승인된 데이터입니다.");
                };
                result += preMileageReqMapper.updateMileageReq(mileageReqDto);
            }
        }

        return result;
    }

    public int deletePreMileageReq(MileageReqDto mileageReqDto) {
        return preMileageReqMapper.deletePreMileageReq(mileageReqDto);
    }
}
