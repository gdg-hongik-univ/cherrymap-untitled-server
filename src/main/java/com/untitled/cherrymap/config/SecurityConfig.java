package com.untitled.cherrymap.config;

import com.untitled.cherrymap.service.KakaoOAuth2MemberService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoOAuth2MemberService kakaoOAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(
                                "/api/**", "/api/login/oauth2/**", "/api/logout", "/api/error" // CSRF 예외 추가
                        )
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // /api/** 경로는 인증 불필요
                        .anyRequest().authenticated() // 그 외 경로는 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .baseUri("/api/oauth2/authorization")
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2MemberService)
                        )
                        .successHandler((request, response, authentication) -> {
                            String requestURI = request.getRequestURI();
                            if (requestURI.startsWith("/api")) {
                                // /api/** 경로에 대해 리다이렉트 방지
                                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                                response.getWriter().write("{\"error\": \"Authentication required\"}");
                            } else {
                                response.sendRedirect("/home");
                            }
                        })
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessUrl("/logout-success")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );
        return http.build();
    }
}
