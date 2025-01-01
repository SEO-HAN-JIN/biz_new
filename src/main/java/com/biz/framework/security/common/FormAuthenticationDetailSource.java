package com.biz.framework.security.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class FormAuthenticationDetailSource implements AuthenticationDetailsSource<HttpServletRequest, FormWebAuthenticationDetails> {

    @Override
    public FormWebAuthenticationDetails buildDetails(HttpServletRequest context) {
        return new FormWebAuthenticationDetails(context);
    }
}
