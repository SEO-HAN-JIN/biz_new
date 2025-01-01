package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {

    List<CamelCaseMap> findCustomers(CustomerDto customerDto);
    CamelCaseMap findCustomerInfo(CustomerDto customerDto);

    int saveCustomer(CustomerDto customerDto);

    int deleteCustomer(CustomerDto customerDto);

    int checkDupl(CustomerDto customerDto);

    int findMileageByBizNo(@Param("coCode") String coCode, @Param("bizNo") String bizNo);

    void updateMileage(@Param("coCode") String coCode, @Param("bizNo") String bizNo, @Param("mileage") int mileage);

    void updateFinalMileage(@Param("coCode") String coCode, @Param("bizNo") String bizNo, @Param("mileage") int mileage);
}
