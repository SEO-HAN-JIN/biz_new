package com.biz.framework.dto.system;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto extends BaseDto {

    private String coCode;
    private String roleId;
    private String roleNm;
    private String roleDesc;

    private String userId;
    private String useNm;
}
