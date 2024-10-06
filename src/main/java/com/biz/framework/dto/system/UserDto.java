package com.biz.framework.dto.system;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto extends BaseDto {
    private String userId;
    private String userPw;
    private String userNm;
}
