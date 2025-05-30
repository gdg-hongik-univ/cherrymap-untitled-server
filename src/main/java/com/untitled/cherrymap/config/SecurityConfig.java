package com.untitled.cherrymap.config;

import com.untitled.cherrymap.jwt.CustomLogoutFilter;
import com.untitled.cherrymap.jwt.JWTFilter;
import com.untitled.cherrymap.jwt.JWTUtil;
import com.untitled.cherrymap.jwt.LoginFilter;
import com.untitled.cherrymap.repository.MemberRepository;
import com.untitled.cherrymap.repository.auth.RefreshRepository;
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
                        .requestMatchers("/", "/login", "/join","/check-nickname", "/reissue","/logout").permitAll()
                        // 관리자 권한이 필요한 경로
                        .requestMatchers("/admin").hasRole("ADMIN")
                        // 그 외 모든 요청은 인증 필요
                        .anyRequest().authenticated()
                )
                // JWT 토큰 필터: 인증된 요청을 처리
                .addFilterBefore(new JWTFilter(jwtUtil, memberRepository), LoginFilter.class)
                // 로그인 필터: 로그인 시 토큰 생성
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, refreshRepository),
                        UsernamePasswordAuthenticationFilter.class)
                // 로그아웃 필터: 리프레시 토큰 제거
                .addFilterBefore(new CustomLogoutFilter(jwtUtil, refreshRepository), LogoutFilter.class)
                // 세션 생성 안 함 (JWT 방식이므로 무상태)
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

}
