package com.untitled.cherrymap.config;

import com.untitled.cherrymap.service.KakaoOAuth2MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

import java.io.IOException;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoOAuth2MemberService kakaoOAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**", "/api/login/oauth2/**", "/api/logout", "/api/error")
                )
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll() // 모든 요청을 인증 없이 허용
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2MemberService)
                        )
                        .successHandler(this::handleLoginSuccess) // 로그인 성공 시 providerId로 리다이렉트
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 안 함
                )
                .logout(logout -> logout
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler(this::handleLogoutSuccess) // 로그아웃 후 리다이렉트
                );

        return http.build();
    }
    // 로그인 성공 후 providerId를 가져와서 /{providerId}/home으로 리다이렉트
    private void handleLoginSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication == null || authentication.getPrincipal() == null) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Authentication principal is null");
            return;
        }

        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // providerId 가져오기
        String providerId = attributes.getOrDefault("id", "Unknown").toString();
        if (providerId.equals("Unknown")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Provider ID is missing");
            return;
        }

        String redirectUrl = "/" + providerId + "/home";
        response.sendRedirect(redirectUrl);
    }

    // 로그아웃 성공 후 /logout-success로 리다이렉트
    private void handleLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        response.sendRedirect("/logout-success");
    }
}
