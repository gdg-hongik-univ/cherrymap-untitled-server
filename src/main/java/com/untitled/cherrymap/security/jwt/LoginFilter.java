package com.untitled.cherrymap.security.jwt;

import com.untitled.cherrymap.security.jwt.refresh.Refresh;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import com.untitled.cherrymap.repository.auth.RefreshRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

// 사용자의 로그인 요청을 가로채어 인증 처리
// 성공 시 JWT AccessToken 및 RefreshToken 발급
// RefreshToken은 DB에 저장하고, 응답에 쿠키로 포함
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;

    // 로그인 인증 시도
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");

        System.out.println("🔐 로그인 시도 nickname: " + nickname);

        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(nickname, password);

        return authenticationManager.authenticate(authToken);
    }


    // 로그인 성공 이후
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication){
        CustomUserDetailsDTO customUserDetailsDTO = (CustomUserDetailsDTO) authentication.getPrincipal();

        // 유저 정보 꺼내 오기
        // username
        String nickname = customUserDetailsDTO.getUsername();

        // role
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();

        // 토큰 생성
        String access = jwtUtil.createJwt("access",nickname, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh",nickname, role, 86400000L);

        //Refresh 토큰 저장
        addRefresh(nickname, refresh, 86400000L);

        // 응답 생성
        response.addHeader("access", access);
        response.addCookie(createCookie("refresh",refresh));
        response.setStatus(HttpStatus.OK.value());
    }

    // 로그인 실패 이후
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed){
        response.setStatus(401);
    }

    // HttpOnly 쿠키 생성
    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24*60*60);
        //cookie.setSecure(true);
        //cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    // Refresh 토큰 DB 저장
    private void addRefresh(String nickname, String refreshToken, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        String expiration = date.toString();

        Refresh refresh = Refresh.builder()
                .nickname(nickname)
                .refresh(refreshToken)
                .expiration(expiration)
                .build();

        refreshRepository.save(refresh);
    }
}
