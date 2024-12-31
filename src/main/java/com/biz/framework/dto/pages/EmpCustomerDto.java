package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpCustomerDto extends BaseDto {

    private String empId;
    private String bizNo;

    private String searchEmpId;
    private String searchCustId;
}
