package com.biz.framework.security.service;

import com.biz.framework.security.dto.AuthenticationDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final AuthenticationDto authenticationDto;

    public UserContext(AuthenticationDto authenticationDto, Collection<? extends GrantedAuthority> authorities) {
        super(authenticationDto.getUserId(), authenticationDto.getUserPw(), authorities);
        this.authenticationDto = authenticationDto;
    }

    public AuthenticationDto getAuthenticationDto() {
        return authenticationDto;
    }
}
