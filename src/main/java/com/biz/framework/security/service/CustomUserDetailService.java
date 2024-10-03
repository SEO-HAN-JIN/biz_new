package com.biz.framework.security.service;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.system.RoleMapper;
import com.biz.framework.mapper.system.UserMapper;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userDto = new UserDto();
        userDto.setUserId(username);
        UserDto user = userMapper.findByUserId(userDto);

        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setUserId(user.getUserId());
        List<CamelCaseMap> roleList = roleMapper.findRoleUser(roleDto);
        List<GrantedAuthority> roles = new ArrayList<>();
        for (CamelCaseMap camelCaseMap : roleList) {
            String roleId = String.valueOf(camelCaseMap.get("roleId"));
            roles.add(new SimpleGrantedAuthority(roleId));
        }

        UserContext userContext = new UserContext(user, roles);
        return userContext;

    }
}
