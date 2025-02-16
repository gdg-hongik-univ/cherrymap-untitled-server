package com.untitled.cherrymap.config;

import com.untitled.cherrymap.service.KakaoOAuth2MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final KakaoOAuth2MemberService kakaoOAuth2MemberService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login/oauth2/**", "/api/**") // 특정 경로만 제외
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login/oauth2/code/kakao", "/error").permitAll() // 인증 불필요 경로
                        .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2MemberService) // 사용자 정보 처리
                        )
                        .successHandler((request, response, authentication) -> {
                            OAuth2User user = (OAuth2User) authentication.getPrincipal();
                            String memberId = user.getAttribute("id"); // 사용자 고유 ID: memberId(카카오에서 제공)
                            response.sendRedirect("/" + memberId + "/home");
                        })

                ).logout(logout -> logout
                        .logoutUrl("/logout") // 로그아웃 엔드포인트
                        .logoutSuccessUrl("/logout-success") // 로그아웃 성공 후 리다이렉트 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll() // 모든 사용자 접근 허용
                );
        return http.build();
    }
}
