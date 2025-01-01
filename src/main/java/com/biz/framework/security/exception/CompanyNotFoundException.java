package com.biz.framework.security.exception;

import org.springframework.security.core.AuthenticationException;

public class CompanyNotFoundException extends AuthenticationException {

    public CompanyNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public CompanyNotFoundException(String msg) {
        super(msg);
    }
}
