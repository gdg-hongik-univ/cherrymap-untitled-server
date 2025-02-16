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
                        .ignoringRequestMatchers(
                                "/api/login/oauth2/**", "/api/logout")
                )
                .authorizeHttpRequests(auth -> auth
                // /api 경로 및 하위 경로 모두 인증 없이 접근 가능
                .requestMatchers("/", "/**", "/api/login/oauth2/code/kakao", "/api/error").permitAll()
                .anyRequest().authenticated() // 나머지 경로는 인증 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .authorizationEndpoint(authEndpoint -> authEndpoint
                                .baseUri("/api/oauth2/authorization") // 엔드포인트 경로 변경
                        )
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(kakaoOAuth2MemberService) // 사용자 정보 처리
                        )
                        .successHandler((request, response, authentication) -> {
                            OAuth2User user = (OAuth2User) authentication.getPrincipal();
                            String providerId = user.getAttribute("id"); // 사용자 고유 ID: memberId(카카오에서 제공)
                            response.sendRedirect("/" + providerId + "/home");
                        })
                )
                .logout(logout -> logout
                        //.logoutRequestMatcher(new AntPathRequestMatcher("/api/logout", "GET")) // GET 요청으로 로그아웃 허용(로컬 테스트 이후에 삭제 필요)
                        .logoutUrl("/api/logout") // POST 요청만 허용 (웹 브라우저에서 로컬 테스트 시 주석 처리 필수)
                        .logoutSuccessUrl("/logout-success") // 로그아웃 성공 후 리다이렉트 경로
                        .invalidateHttpSession(true) // 세션 무효화
                        .deleteCookies("JSESSIONID") // 쿠키 삭제
                        .permitAll() // 모든 사용자 접근 허용
                );
        return http.build();
    }
}
