package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TaxinvoiceslineDto extends BaseDto {


    private String confirmSeq;
    private String taxKey;
    private Integer no;              // 순번
    private String description;     // 품목
    private String quantity;        // 수량
    private String unitPrice;       // 단가
    private String supplyAmount;    // 공급가액
    private String taxAmount;       // 세액
}
