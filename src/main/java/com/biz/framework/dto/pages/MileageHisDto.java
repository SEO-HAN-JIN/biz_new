package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MileageHisDto extends BaseDto {

    private String bizNo;
    private String empId;
    private int mileagePrev;
    private int mileageAft;
    private String createdDate;
    private String createdPage;
    private String createdId;

    private int mileageAmt;
}
