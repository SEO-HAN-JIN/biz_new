package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmpAgencyDto extends BaseDto {

    private String empId;
    private String agencyId;
    private String agencyName;
    private String agencyIncentiveRate;
    private String prodAmt;

    private String isAdmin;

    private String searchEmpId;
    private String searchEmpNm;
    private String searchAgencyId;
}
