package com.biz.framework.dto;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuDto extends BaseDto {
    private String menuId;
    private String menuNm;
    private String menuSrtOrd;
    private String menuUpId;

    private List<MenuDto> _children;    // tree grid를 만들기 위해
}
