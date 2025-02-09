package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AgencyProductDto extends BaseDto {

    private String agencyId;
    private String prodId;
    private String agencyName;
    private String prodAmt;

    private String isAdmin;

    private String searchAgencyId;
    private String searchAgencyName;
    private String searchProdId;
}
