package com.biz.framework.security.service;

import com.biz.framework.dto.system.UserDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserContext extends User {

    private final UserDto user;

    public UserContext(UserDto user, Collection<? extends GrantedAuthority> authorities) {
        super(user.getUserId(), user.getUserPw(), authorities);
        this.user = user;
    }

    public UserDto getUser() {
        return user;
    }
}
