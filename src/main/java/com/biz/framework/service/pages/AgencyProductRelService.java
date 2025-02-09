package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.AgencyProductDto;
import com.biz.framework.mapper.pages.AgencyProductRelMapper;
import com.biz.framework.mapper.pages.EmpAgencyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AgencyProductRelService {

    private final AgencyProductRelMapper agencyProductRelMapper;

    public List<CamelCaseMap> findAgencyList(AgencyProductDto agencyProductDto) {
        return agencyProductRelMapper.findAgencyList(agencyProductDto);
    }

    public List<CamelCaseMap> findAgencyProductList(AgencyProductDto agencyProductDto) {
        return agencyProductRelMapper.findAgencyProductList(agencyProductDto);
    }

    public int saveEmpAgencyList(List<AgencyProductDto> agencyProductDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(agencyProductDtoList)) {
            for (AgencyProductDto dto : agencyProductDtoList) {
                if (agencyProductRelMapper.checkDupl(dto) > 0) {
                    throw new ServiceException("중복되는 데이터가 존재합니다.\n대행사번호: " + dto.getAgencyId());
                }
                result += agencyProductRelMapper.saveEmpAgencyList(dto);
            }
        }
        return result;
    }

    public int deleteEmpAgencyList(List<AgencyProductDto> agencyProductDtoList) {
        int result = 0;

        if (!CollectionUtils.isEmpty(agencyProductDtoList)) {
            for (AgencyProductDto dto : agencyProductDtoList) {
                result += agencyProductRelMapper.deleteEmpAgencyList(dto);
            }
        }

        return result;
    }

    public List<CamelCaseMap> findAgProductList(AgencyProductDto agencyProductDto) {
        return agencyProductRelMapper.findAgProductList(agencyProductDto);
    }
}
