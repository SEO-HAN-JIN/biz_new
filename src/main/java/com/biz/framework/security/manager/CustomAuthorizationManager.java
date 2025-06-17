package com.biz.framework.security.manager;

import com.biz.framework.common.map.CamelCaseMap;
import com.biz.framework.dto.system.ProgramDto;
import com.biz.framework.mapper.system.RoleMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    private static final String REQUIRED_ROLE = "ROLE_SECURE";
    private final RoleMapper roleMapper;

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        HttpServletRequest request = object.getRequest();
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api") || requestURI.startsWith("/pages")) {
            String referer = request.getHeader("Referer");

            // 프로토콜 (http 또는 https), 서버 이름, 포트 정보를 동적으로 구성하여 제거
            String serverInfo = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            requestURI = referer.replaceFirst(serverInfo, ""); // 서버 정보 제거
            return new AuthorizationDecision(true);
        }

        ProgramDto programDto = new ProgramDto();
        programDto.setProgramUrl(requestURI);
        List<CamelCaseMap> requiredRoles = roleMapper.findRoleProgram(programDto);

        Authentication auth = authentication.get();
        if (auth == null || !auth.isAuthenticated()) {
            return new AuthorizationDecision(false);
        }

        Set<String> userRoles = auth.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        boolean hasRequiredRole = requiredRoles.stream().map(role -> role.get("roleId").toString()).anyMatch(userRoles::contains);

        return new AuthorizationDecision(hasRequiredRole);
    }
}
