package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto extends BaseDto {

    private String bizNo;
    private String empId;
    private String custName;
    private String ownerName;
    private String custAddr;
    private String custTel;
    private String custMail;
    private String depositorName;
    private int mileage;
    private int mileagePrev;
    private String regDate;
    private String useInd;

    private String empName;

    @JsonProperty("isNew")
    private boolean isNew;
}
