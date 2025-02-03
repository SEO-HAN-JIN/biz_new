package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AgencyReqDto extends BaseDto {

    private String coCode;
    private String agencySeq;
    private String userId;
    private String agencyId;
    private String reqDate;
    private String incentiveRate;
    private String prodName;
    private String inflowCnt;
    private String prodAmt;
    private String saleTotalAmt;
    private String expectAmt;
    private String applyStatus;

    private String agencyName;
    private String searchStartDate;
    private String searchEndDate;

    private List<AgencyReqDto> agencyReqDtoList;

}
