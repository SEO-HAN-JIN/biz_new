package com.biz.framework.security.service;

import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;

//    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        UserDto userDto = new UserDto();
//        userDto.setUserId(username);
//        userMapper.findByUserId(userDto);
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return List.of();
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public String getUsername() {
                return "";
            }
        };
    }
}
