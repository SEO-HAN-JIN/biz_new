package com.biz.framework.dto;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProgramDto extends BaseDto {

    private String programId;
    private String programNm;
    private String programUrl;
    private String useYn;
    private String programSrtOrd;

    private String menuId;
}
