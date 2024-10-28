package com.biz.framework.common.dto;

import com.biz.framework.security.dto.AuthenticationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;

@Getter
@Setter
public abstract class BaseDto {

    private RowStatus rowStatus;

    private String loginUserId;      //로그인한 사용자ID
    private String loginUserNm;      //로그인한 사용자명

    public BaseDto() {
        try {
            String userId = null;
            String userNm = null;

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if(authentication != null) {
                Object principal = authentication.getPrincipal();
                if(principal instanceof String){
                    userId = principal.toString();
                }else if(principal instanceof AuthenticationDto){

                    AuthenticationDto authenticationDto = (AuthenticationDto) principal;
                    userId = authenticationDto.getUserId();
                    userNm = authenticationDto.getUserNm();
                }
            }
            this.loginUserId = userId == null ? "__anonymous__" : userId;
            this.loginUserNm = userNm == null ? "__anonymous__" : userNm;

        } catch (IllegalStateException e) {
            this.loginUserId = "error";
            this.loginUserNm = "error";
        }
    }
}
