package com.biz.framework.security.config;

//import com.biz.framework.security.service.CustomUserDetailService;
import com.biz.framework.security.handler.CustomAccessDeniedHandler;
import com.biz.framework.security.manager.CustomAuthorizationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final CustomAuthorizationManager customAuthorizationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)  // 권장되는 CSRF 비활성화 설정 방식
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/login*", "/css/**", "/js/**", "/images/**", "/assets/**", "/config/**", "/static/**", "/denied*", "/api/sidelayout").permitAll()  // 정적 자원 및 로그인 페이지 허용
                        .requestMatchers("/").authenticated()
                        .anyRequest().access(customAuthorizationManager)
                )
                .formLogin(login -> login
                        .loginPage("/login")
                        .usernameParameter("userId")
                        .passwordParameter("userPw")
                        .loginProcessingUrl("/login_proc")
                        .defaultSuccessUrl("/", true)
                        .failureHandler(authenticationFailureHandler)
                        .permitAll()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(accessDeniedHandler()))
        ;
        return http.build();
    }

    // 평문 비밀번호를 사용하기 위한 PasswordEncoder 설정
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler customAccessDeniedHandler = new CustomAccessDeniedHandler();
        customAccessDeniedHandler.setErrorPage("/denied");
        return customAccessDeniedHandler;
    }

}
