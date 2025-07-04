package com.untitled.cherrymap.security.jwt.refresh;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.security.exception.refreshException.ExpiredRefreshTokenException;
import com.untitled.cherrymap.security.exception.refreshException.InvalidRefreshTokenException;
import com.untitled.cherrymap.security.jwt.JwtProperties;
import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

// 클라이언트가 제출한 Refresh 토큰 검증
// Access 토큰과 새 Refresh 토큰 재발급
@Service
@RequiredArgsConstructor
public class ReissueService {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final JwtProperties jwtProperties;

    public SuccessResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 쿠키에서 Refresh 토큰 추출
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refresh".equals(cookie.getName())) {
                    refresh = cookie.getValue();
                    break;
                }
            }
        }

        if (refresh == null) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw ExpiredRefreshTokenException.EXCEPTION;
        }

        if (!"refresh".equals(jwtUtil.getCategory(refresh))) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        if (!refreshRepository.existsByRefresh(refresh)) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        String nickname = jwtUtil.getNickname(refresh);
        String role = jwtUtil.getRole(refresh);

        Long accessExp = jwtProperties.getAccessTokenExpirationMs();
        Long refreshExp = jwtProperties.getRefreshTokenExpirationMs();

        String newAccess = jwtUtil.createJwt("access", nickname, role, accessExp);
        String newRefresh = jwtUtil.createJwt("refresh", nickname, role, refreshExp);

        refreshRepository.deleteByRefresh(refresh);
        addRefresh(nickname, newRefresh, refreshExp);

        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return SuccessResponse.success(200, Map.of("message", "토큰이 성공적으로 재발급되었습니다."));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int)(jwtProperties.getRefreshTokenExpirationMs() / 1000)); // 초 단위로 변환
        cookie.setHttpOnly(true);
        return cookie;
    }

    private void addRefresh(String nickname, String refreshToken, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refresh = Refresh.builder()
                .nickname(nickname)
                .refresh(refreshToken)
                .expiration(date.toString())
                .build();

        refreshRepository.save(refresh);
    }
}

