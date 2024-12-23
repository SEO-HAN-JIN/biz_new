package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MileageReqDto extends BaseDto {

    private String reqNo;
    private String userId;
    private String custId;
    private String reqAmt;
    private String approveInd;
    private String reqDate;

    private String userName;
    private String custName;

}
