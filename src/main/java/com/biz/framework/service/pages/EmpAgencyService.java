package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.EmpAgencyDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import com.biz.framework.mapper.pages.EmpAgencyMapper;
import com.biz.framework.mapper.pages.EmpCustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmpAgencyService {

    private final EmpAgencyMapper empCustomerMapper;

    public List<CamelCaseMap> findEmplList(EmpAgencyDto empAgencyDto) {
        return empCustomerMapper.findEmplList(empAgencyDto);
    }

    public List<CamelCaseMap> findEmpAgencyList(EmpAgencyDto empAgencyDto) {
        return empCustomerMapper.findEmpAgencyList(empAgencyDto);
    }

    public int saveEmpAgencyList(List<EmpAgencyDto> empAgencyDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(empAgencyDtoList)) {
            for (EmpAgencyDto dto : empAgencyDtoList) {
                if (empCustomerMapper.checkDupl(dto) > 0) {
                    throw new ServiceException("중복되는 데이터가 존재합니다.\n대행사번호: " + dto.getAgencyId());
                }
                result += empCustomerMapper.saveEmpAgencyList(dto);
            }
        }
        return result;
    }

    public int deleteEmpAgencyList(List<EmpAgencyDto> empAgencyDtoList) {
        int result = 0;

        if (!CollectionUtils.isEmpty(empAgencyDtoList)) {
            for (EmpAgencyDto dto : empAgencyDtoList) {
                result += empCustomerMapper.deleteEmpAgencyList(dto);
            }
        }

        return result;
    }

    public List<CamelCaseMap> findAgencyList(EmpAgencyDto empAgencyDto) {
        return empCustomerMapper.findAgencyList(empAgencyDto);
    }
}
