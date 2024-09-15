package com.biz.framework.dto;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuDto extends BaseDto {
    private String menuId;
    private String menuNm;
    private String menuSrtOrd;
    private String menuUpId;
}
