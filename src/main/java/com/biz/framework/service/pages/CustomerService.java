package com.biz.framework.service.pages;

import com.biz.framework.common.exception.ServiceException;
import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final MileageHisService mileageHisService;

    public List<CamelCaseMap> findCustomers(CustomerDto customerDto) {

        AuthenticationDto authenticationDto = (AuthenticationDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<GrantedAuthority> authorities = authenticationDto.getAuthorities();

        if(!authorities.stream()
                .map(GrantedAuthority::getAuthority) // 권한 이름 가져오기
                .anyMatch(auth -> auth.equals("1") || auth.equals("2")))
        {
            customerDto.setEmpId(customerDto.getLoginUserId());
        }

        return customerMapper.findCustomers(customerDto);
    }

    public int saveCustomer(CustomerDto customerDto) {
        int result = 0;

        if (customerDto.isNew()) {
            if (0 < customerMapper.checkDupl(customerDto)) {
                throw new ServiceException("동일한 사업번호와 직원의 데이터가 존재합니다.");
            }
        }
        if (customerDto.isNew() || customerDto.getMileagePrev() != customerDto.getMileage()) {
            MileageHisDto mileageHisDto = new MileageHisDto();
            mileageHisDto.setBizNo(customerDto.getBizNo());
            mileageHisDto.setEmpId(customerDto.getEmpId());
            mileageHisDto.setMileagePrev(customerDto.getMileage());
            mileageHisDto.setMileageAft(customerDto.getMileage());
            mileageHisDto.setCreatedPage("CU");
            mileageHisDto.setCreatedId(customerDto.getLoginUserId());
            result += mileageHisService.saveMileageHis(mileageHisDto);
        }
        result+= customerMapper.saveCustomer(customerDto);
        return result;
    }

    public int deleteCustomer(CustomerDto customerDto) {
        int result = 0;
        result += customerMapper.deleteCustomer(customerDto);
        return result;
    }

    public CamelCaseMap findCustomerInfo(CustomerDto customerDto) {
        return customerMapper.findCustomerInfo(customerDto);
    }

    public List<CamelCaseMap> findCustomerByLoginId(CustomerDto customerDto) {
        return customerMapper.findCustomerByLoginId(customerDto);
    }
}
