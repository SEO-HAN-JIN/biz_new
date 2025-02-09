package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.*;
import com.biz.framework.dto.pages.AgencyReqDto;
import com.biz.framework.mapper.pages.AgencyReqMapper;
import com.biz.framework.mapper.pages.ApplypaymentMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.MileageHisMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyReqService {

    private final AgencyReqMapper agencyReqMapper;

    public List<CamelCaseMap> findApplypayment(AgencyReqDto agencyReqDto) {
        return agencyReqMapper.findApplypayment(agencyReqDto);
    }

    /*
        환불요청인 경우에도 해당 메서드 사용 중 (컨트롤러에서 요청구분, 승인여부 세팅)
     */
    public int saveApplypayment(AgencyReqDto agencyReqDto) {
        int result = 0;

        switch (agencyReqDto.getRowStatus()) {
            case C -> {
                // 요청번호 채번
                agencyReqDto.setAgencySeq(agencyReqMapper.createAgencySeq(agencyReqDto));
                result += agencyReqMapper.saveAgencyReq(agencyReqDto);
            }
            case U -> {
                result += agencyReqMapper.updateAgencyReq(agencyReqDto);
            }
        }
        return result;
    }

    public int deleteSettlement(AgencyReqDto agencyReqDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(agencyReqDto.getAgencyReqDtoList()))
        {
            for(AgencyReqDto dto : agencyReqDto.getAgencyReqDtoList())
            {
                if (!"01".equals(agencyReqMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("승인요청건만 삭제 가능합니다.");
                }
                result += agencyReqMapper.deleteSettlement(dto);
            }
        }


        return result;
    }

    public List<CamelCaseMap> findSettlement(AgencyReqDto agencyReqDto) {
        return agencyReqMapper.findSettlement(agencyReqDto);
    }

    public int cancelSettlement(AgencyReqDto agencyReqDto) {
        int result = 0;

        if(!CollectionUtils.isEmpty(agencyReqDto.getAgencyReqDtoList()))
        {
            for(AgencyReqDto dto : agencyReqDto.getAgencyReqDtoList())
            {
                if (!"01".equals(agencyReqMapper.checkApplyStatus(dto))) {
                    throw new ServiceException("승인요청건만 삭제 가능합니다.");
                }

                result += agencyReqMapper.cancelSettlement(dto);
            }
        }


        return result;
    }
}
