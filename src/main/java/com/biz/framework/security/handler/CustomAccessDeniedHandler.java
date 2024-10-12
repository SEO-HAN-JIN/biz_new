package com.biz.framework.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // X-Requested-With 또는 Accept 헤더를 통해 AJAX 요청인지 확인
        String ajaxHeader = request.getHeader("X-Requested-With");
        String acceptHeader = request.getHeader("Accept");

        boolean isAjax = "XMLHttpRequest".equals(ajaxHeader) || (acceptHeader != null && acceptHeader.contains("application/json"));

        if (isAjax) {
            // AJAX 요청에 대해 JSON 응답 반환
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"message\": \"Access Denied\"}");
        } else {
            // 일반 요청에 대해서는 /denied로 리디렉트
            response.sendRedirect(errorPage);
        }

    }

    public void setErrorPage(String errorPage) {
        this.errorPage = errorPage;
    }
}
