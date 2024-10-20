package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDto extends BaseDto {

    private String bizNo;
    private String userId;
    private String custName;
    private String ownerName;
    private String custAddr;
    private String custTel;
    private String custMail;
    private String depositorName;
    private String mileage;
    private String regDate;
    private String useInd;
}
