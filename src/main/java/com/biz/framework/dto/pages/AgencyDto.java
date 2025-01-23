package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgencyDto extends BaseDto {

    private String coCode;
    private String agencyId;
    private String agencyName;
    private String agencyIncentiveRate;
    private String prodAmt;
    private String useInd;

    @JsonProperty("isNew")
    private boolean isNew;
}
