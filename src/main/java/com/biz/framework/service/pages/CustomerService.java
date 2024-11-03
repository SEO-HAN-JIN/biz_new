package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.MileageHisDto;
import com.biz.framework.mapper.pages.CustomerMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final MileageHisService mileageHisService;

    public List<CamelCaseMap> findCustomers(CustomerDto customerDto) {
        return customerMapper.findCustomers(customerDto);
    }

    public int saveCustomer(CustomerDto customerDto) {
        int result = 0;
        if (customerDto.isNew() || customerDto.getMileagePrev() != customerDto.getMileage()) {
            MileageHisDto mileageHisDto = new MileageHisDto();
            mileageHisDto.setBizNo(customerDto.getBizNo());
            mileageHisDto.setMileagePrev(customerDto.getMileagePrev());
            mileageHisDto.setMileageAft(customerDto.getMileage());
            mileageHisDto.setCreatedId("CU");
            result += mileageHisService.saveMileageHis(mileageHisDto);
        }
        result+= customerMapper.saveCustomer(customerDto);
        return result;
    }

    public int deleteCustomer(CustomerDto customerDto) {
        return customerMapper.deleteCustomer(customerDto);
    }
}
