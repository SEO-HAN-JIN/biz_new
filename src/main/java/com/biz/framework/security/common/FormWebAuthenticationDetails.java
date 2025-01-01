package com.biz.framework.security.common;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class FormWebAuthenticationDetails extends WebAuthenticationDetails {

    private String coCode;

    public FormWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        coCode = request.getParameter("coCode");
    }

    public String getCoCode() {
        return coCode;
    }
}
