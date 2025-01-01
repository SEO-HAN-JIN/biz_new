package com.biz.framework.security.service;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.system.RoleMapper;
import com.biz.framework.mapper.system.UserMapper;
import com.biz.framework.security.dto.AuthenticationDto;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        request.getSE
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

        AuthenticationDto auth = AuthenticationDto.builder()
                .coCode(user.getCoCode())
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userNm(user.getUserNm())
                .authorities(roles)
                .build();
        return new UserContext(auth, roles);
    }
}
