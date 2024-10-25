package com.biz.framework.service.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
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

    public List<CamelCaseMap> findCustomers(CustomerDto customerDto) {
        return customerMapper.findCustomers(customerDto);
    }

    public int saveCustomer(CustomerDto customerDto) {
        return customerMapper.saveCustomer(customerDto);
    }

    public int deleteCustomer(CustomerDto customerDto) {
        return customerMapper.deleteCustomer(customerDto);
    }
}
