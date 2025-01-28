package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyDto;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.mapper.pages.AgencyMapper;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.mapper.pages.EmpCustomerMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyService {

    private final AgencyMapper agencyMapper;

    public List<CamelCaseMap> findAgency(AgencyDto agencyDto) {
        return agencyMapper.findAgency(agencyDto);
    }

    public int saveAgency(AgencyDto agencyDto) {
        int result = 0;

        if (agencyDto.isNew()) {
            result+= agencyMapper.saveAgency(agencyDto);
        } else {
            result += agencyMapper.updateAgency(agencyDto);
        }
        return result;
    }

    public int deleteAgency(AgencyDto agencyDto) {
        int result = 0;
        result += agencyMapper.deleteAgency(agencyDto);
        return result;
    }

    /**
     * 대행사 리스트 조회용
     * @param agencyDto
     * @return
     */
    public List<CamelCaseMap> findAgencyList(AgencyDto agencyDto) {
        return agencyMapper.findAgencyList(agencyDto);
    }

    /**
     * 대행사 인센티브
     * @param agencyDto
     * @return
     */
    public CamelCaseMap findAgencyIncentiveRate(AgencyDto agencyDto) {
        return agencyMapper.findAgencyIncentiveRate(agencyDto);
    }
}
