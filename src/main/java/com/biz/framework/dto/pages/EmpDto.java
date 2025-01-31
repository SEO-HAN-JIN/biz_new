package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpDto extends BaseDto {

    private String coCode;
    private String empId;
    private String empName;
    private String entranceDate;
    private String retireDate;
    private String gradeCode;
    private String empTel;
    private String empMail;
    private float incentiveRate;
    private String curPassword;
    private String newPassword;
    private String password;

    @JsonProperty("isNew")
    private boolean isNew;
}
