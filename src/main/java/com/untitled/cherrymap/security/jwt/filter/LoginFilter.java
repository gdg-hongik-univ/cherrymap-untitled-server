package com.untitled.cherrymap.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untitled.cherrymap.common.dto.ErrorResponse;
import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.security.exception.loginException.LoginFailedException;
import com.untitled.cherrymap.security.jwt.JwtProperties;
import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import com.untitled.cherrymap.security.jwt.refresh.Refresh;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import com.untitled.cherrymap.security.jwt.refresh.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

/**
 * 로그인 요청을 가로채 인증을 수행하고,
 * 성공 시 AccessToken/RefreshToken을 발급하여 헤더와 쿠키에 담아 반환
 */
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;
    private final JwtProperties jwtProperties;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        if (nickname == null || password == null) {
            throw LoginFailedException.EXCEPTION;
        }

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(nickname, password);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                         FilterChain chain, Authentication authentication) throws IOException {

        // 사용자 정보
        CustomUserDetailsDTO userDetails = (CustomUserDetailsDTO) authentication.getPrincipal();
        Member member = userDetails.getMember(); // 직접 member 엔티티 사용
        String role = authentication.getAuthorities().iterator().next().getAuthority();

        // 토큰 만료 시간
        Long accessExp = jwtProperties.getAccessTokenExpirationMs();
        Long refreshExp = jwtProperties.getRefreshTokenExpirationMs();

        // memberId를 기반으로 JWT 생성
        String access = jwtUtil.createJwt("access", member.getId().toString(), role, accessExp);
        String refresh = jwtUtil.createJwt("refresh", member.getId().toString(), role, refreshExp);

        // refresh 저장
        addRefresh(member, refresh, refreshExp);

        // 응답 설정
        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        response.addHeader("access", access);
        response.addCookie(createCookie("refresh", refresh, refreshExp));

        SuccessResponse<?> successResponse = SuccessResponse.success(200, Map.of(
                "message", "로그인에 성공했습니다.",
                "nickname", member.getNickname()
        ));

        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }

    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                           AuthenticationException failed) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json; charset=UTF-8");

        ErrorResponse errorResponse = ErrorResponse.of(
                401,
                "LOGIN_401",
                "로그인에 실패했습니다. 아이디 또는 비밀번호를 확인해주세요.",
                request.getRequestURI()
        );

        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }

    private Cookie createCookie(String key, String value, Long maxAgeMs) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int)(maxAgeMs / 1000)); // 초 단위
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        return cookie;
    }

    private void addRefresh(Member member, String refreshToken, Long expiredMs) {
        Refresh refresh = Refresh.builder()
                .member(member)
                .refresh(refreshToken)
                .expiration(LocalDateTime.now().plusSeconds(expiredMs / 1000))
                .build();

        refreshRepository.save(refresh);
    }
}
