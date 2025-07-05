package com.untitled.cherrymap.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untitled.cherrymap.security.jwt.JwtProperties;
import com.untitled.cherrymap.security.jwt.filter.CustomLogoutFilter;
import com.untitled.cherrymap.security.jwt.filter.JWTFilter;
import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import com.untitled.cherrymap.security.jwt.filter.LoginFilter;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.security.jwt.refresh.RefreshRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    //AuthenticationManager Bean 등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository, objectMapper, jwtProperties);
        loginFilter.setFilterProcessesUrl("/api/login");

        http
                // CSRF 보호 비활성화 (JWT 기반이므로 필요 없음)
                .csrf(csrf -> csrf.disable())
                // Form 로그인 방식 비활성화
                .formLogin(form -> form.disable())
                // HTTP Basic 인증 방식 비활성화
                .httpBasic(httpBasic -> httpBasic.disable())
                // 경로별 인가 설정
                .authorizeHttpRequests(auth -> auth
                        // 누구나 접근 가능한 공개 경로
                        .requestMatchers("/", "/index.html", "/api/login", "/api/join","/api/check-nickname", "/api/reissue","/api/logout").permitAll()
                        // Swagger UI 관련 경로 허용
                        .requestMatchers("/cherrymap-ui.html", "/swagger-ui/**", "/api-docs/**", "/v3/api-docs/**").permitAll()
                        // 정적 리소스 허용
                        .requestMatchers("/static/**", "/css/**", "/js/**", "/images/**").permitAll()
                        // 관리자 권한이 필요한 경로
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // JWT 토큰 필터: 인증된 요청을 처리
                .addFilterBefore(new JWTFilter(jwtUtil, memberRepository,objectMapper), LoginFilter.class)
                // 로그인 필터: 로그인 시 토큰 생성
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class)
                // 로그아웃 필터: 리프레시 토큰 제거
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository, objectMapper), LogoutFilter.class)
                // 세션 생성 안 함 (JWT 방식이므로 무상태)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
