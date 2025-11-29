package com.biz.framework.dto.pages;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RefundItemDto extends BaseDto {

    private String searchId;
    private String searchName;
    private String searchUseInd;

    private String refundItemId;
    private String refundItemName;
    private String useInd;
}
