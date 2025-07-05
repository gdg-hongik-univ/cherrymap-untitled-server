package com.untitled.cherrymap.security.jwt.refresh;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.domain.member.domain.Member;
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

import java.time.LocalDateTime;
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
    private final MemberRepository memberRepository;

    public SuccessResponse<?> reissue(HttpServletRequest request, HttpServletResponse response) {

        // 1. 쿠키에서 Refresh Token 추출
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

        // 2. 토큰 만료 여부 확인
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw ExpiredRefreshTokenException.EXCEPTION;
        }

        // 3. 카테고리 확인
        if (!"refresh".equals(jwtUtil.getCategory(refresh))) {
            throw InvalidRefreshTokenException.EXCEPTION;
        }

        // 4. 저장된 refresh token인지 확인
        Refresh stored = refreshRepository.findByRefresh(refresh)
                .orElseThrow(() -> InvalidRefreshTokenException.EXCEPTION);

        Member member = stored.getMember();

        // 5. 새 토큰 생성
        Long accessExp = jwtProperties.getAccessTokenExpirationMs();
        Long refreshExp = jwtProperties.getRefreshTokenExpirationMs();

        String newAccess = jwtUtil.createJwt("access", member.getId().toString(), member.getRole().toString(), accessExp);
        String newRefresh = jwtUtil.createJwt("refresh", member.getId().toString(), member.getRole().toString(), refreshExp);

        // 6. 기존 토큰 삭제 및 새 토큰 저장
        refreshRepository.deleteByRefresh(refresh);
        addRefresh(member, newRefresh, refreshExp);

        // 7. 응답에 토큰 포함
        response.setHeader("access", newAccess);
        response.addCookie(createCookie("refresh", newRefresh));

        return SuccessResponse.success(200, Map.of("message", "토큰이 성공적으로 재발급되었습니다."));
    }

    private Cookie createCookie(String key, String value) {
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge((int) (jwtProperties.getRefreshTokenExpirationMs() / 1000));
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
