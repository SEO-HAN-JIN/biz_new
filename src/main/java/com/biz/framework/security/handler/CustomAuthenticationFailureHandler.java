package com.biz.framework.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errorMsg = "유효하지 않은 아이디 혹은 비밀번호 입니다.";

        // 한글은 인코딩하지 않으면 controller 로 가지 않음.
        errorMsg = URLEncoder.encode(errorMsg, "UTF-8");

        response.sendRedirect("/login?error=true&exception=" + errorMsg);

    }

}
