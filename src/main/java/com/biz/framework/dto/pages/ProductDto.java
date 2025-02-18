package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto extends BaseDto {

    private String prodId;
    private String prodName;
    private String prodAmt;
    private String effDateFrom;
    private String effDateTo;
    private String prodIncentive;
    private String useInd;
    private String baseDate;
}
