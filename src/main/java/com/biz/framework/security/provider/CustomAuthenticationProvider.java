package com.biz.framework.security.provider;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.RoleDto;
import com.biz.framework.dto.system.UserDto;
import com.biz.framework.mapper.system.RoleMapper;
import com.biz.framework.mapper.system.UserMapper;
import com.biz.framework.security.common.FormWebAuthenticationDetails;
import com.biz.framework.security.dto.AuthenticationDto;
import com.biz.framework.security.exception.CompanyNotFoundException;
import com.biz.framework.security.service.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 사용자가 입력한 정보
        FormWebAuthenticationDetails details = (FormWebAuthenticationDetails) authentication.getDetails();
        String coCode = details.getCoCode();
        String userId = authentication.getName();
        String userPw = (String) authentication.getCredentials();

        if (coCode == null) {
            throw new CompanyNotFoundException("CompanyNotFoundException");
        }

        // === loadByUserName Start ====
        // loadByUsername 에 coCode를 못넘겨서 loadByUsername 내용을 여기에다 구현
        UserDto userDto = new UserDto();
        userDto.setCoCode(coCode);
        userDto.setUserId(userId);
        UserDto user = userMapper.findByUserId(userDto);

        if (user == null) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        RoleDto roleDto = new RoleDto();
        roleDto.setCoCode(user.getCoCode());
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

        UserContext userContext = new UserContext(auth, roles);
        // === loadByUserName End ===

//        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(userId);
        AuthenticationDto authenticationDto = userContext.getAuthenticationDto();

//        if (!passwordEncoder.matches(userPw, userContext.getUser().getUserPw())) {
//            throw new BadCredentialsException("BadCredentialException");
//        }
        if (!userPw.equals(authenticationDto.getUserPw())) {
            throw new BadCredentialsException("BadCredentialException");
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(authenticationDto, null, authenticationDto.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
