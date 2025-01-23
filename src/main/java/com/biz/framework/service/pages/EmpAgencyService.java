package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
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

    public List<CamelCaseMap> findEmplList(EmpCustomerDto empCustomerDto) {
        return empCustomerMapper.findEmplList(empCustomerDto);
    }

    public List<CamelCaseMap> findCustoemrList(EmpCustomerDto empCustomerDto) {
        return empCustomerMapper.findCustoemrList(empCustomerDto);
    }

    public int saveEmpCustomerList(List<EmpCustomerDto> empCustomerDtoList) {
        int result = 0;
        if (!CollectionUtils.isEmpty(empCustomerDtoList)) {
            for (EmpCustomerDto dto : empCustomerDtoList) {
                if (empCustomerMapper.checkDupl(dto) > 0) {
                    throw new ServiceException("중복되는 데이터가 존재합니다.\n사업자번호: " + dto.getBizNo());
                }
                result += empCustomerMapper.saveEmpCustomerList(dto);
            }
        }
        return result;
    }

    public int deleteEmpCustomerList(List<EmpCustomerDto> empCustomerDtoList) {
        int result = 0;

        if (!CollectionUtils.isEmpty(empCustomerDtoList)) {
            for (EmpCustomerDto dto : empCustomerDtoList) {
                result += empCustomerMapper.deleteEmpCustomer(dto);
            }
        }

        return result;
    }

    public List<CamelCaseMap> findCustomerListModal(EmpCustomerDto empCustomerDto) {
        return empCustomerMapper.findCustomerListByBizNo(empCustomerDto);
    }
}
