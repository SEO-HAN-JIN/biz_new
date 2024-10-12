package com.biz.framework.security.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@Builder
public class AuthenticationDto {
    private String userId;
    private String userPw;
    private String userNm;
    private List<GrantedAuthority> authorities;
}
