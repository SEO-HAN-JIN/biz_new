package com.biz.framework.common.dto;

import com.biz.framework.security.dto.AuthenticationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Getter
@Setter
public abstract class BaseDto {

    private RowStatus rowStatus;

    private String loginCoId;
    private String loginUserId;      //로그인한 사용자ID
    private String loginUserNm;      //로그인한 사용자명

    public BaseDto() {
        try {
            String coCode = null;
            String userId = null;
            String userNm = null;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null) {
                Object principal = authentication.getPrincipal();
                if(principal instanceof String){
                    userId = principal.toString();
                }else if(principal instanceof AuthenticationDto){

                    AuthenticationDto authenticationDto = (AuthenticationDto) principal;
                    coCode = authenticationDto.getCoCode();
                    userId = authenticationDto.getUserId();
                    userNm = authenticationDto.getUserNm();
                }
            }
            this.loginCoId = coCode;
            this.loginUserId = userId == null ? "__anonymous__" : userId;
            this.loginUserNm = userNm == null ? "__anonymous__" : userNm;

        } catch (IllegalStateException e) {
            this.loginUserId = "error";
            this.loginUserNm = "error";
        }
    }
}
