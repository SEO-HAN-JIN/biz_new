package com.biz.framework.dto.system;

import com.biz.framework.common.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDto extends BaseDto {

    private String coCode;
    private String coName;
    private String bizNo;
    private String ceoName;
    private String companyMail;
    private String companyTel;
    private String companyAddress;
    private String bizType;
    private String bizClass;
    private String useYn;
}
