package com.biz.framework.mapper.pages;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.pages.CustomerDto;
import com.biz.framework.dto.pages.EmpCustomerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface EmpCustomerMapper {

    List<CamelCaseMap> findEmplList(EmpCustomerDto empCustomerDto);

    List<CamelCaseMap> findCustoemrList(EmpCustomerDto empCustomerDto);

    int saveEmpCustomerList(EmpCustomerDto empCustomerDto);

    int checkDupl(EmpCustomerDto empCustomerDto);

    int deleteEmpCustomer(EmpCustomerDto empCustomerDto);

    List<CamelCaseMap> findCustomerListByBizNo(EmpCustomerDto empCustomerDto);
}
