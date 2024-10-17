package com.biz.framework.dto.system;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CodeDto extends BaseDto {

    // cdpatt
    private String patternCode;
    private String patternName;

    // cdbase
    private String baseCode;
    private String codeName;
    private String displayOrder;

}
