package com.biz.framework.security.provider;

import com.biz.framework.security.dto.AuthenticationDto;
import com.biz.framework.security.service.CustomUserDetails;
import com.biz.framework.security.service.UserContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 사용자가 입력한 정보
        String userId = authentication.getName();
        String userPw = (String) authentication.getCredentials();

        UserContext userContext = (UserContext) userDetailsService.loadUserByUsername(userId);
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
