package com.untitled.cherrymap.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untitled.cherrymap.common.dto.ErrorResponse;
import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import com.untitled.cherrymap.security.jwt.refresh.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;
import java.util.Map;

/**
 * POST /api/logout 요청을 처리하는 로그아웃 필터
 * - 쿠키에서 Refresh 토큰을 가져와 Redis에서 제거
 * - 쿠키 삭제 및 성공 메시지 반환
 */
@RequiredArgsConstructor
public class CustomLogoutFilter extends GenericFilterBean {

    private final JWTUtil jwtUtil;
    private final RefreshRepository refreshRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        doFilter((HttpServletRequest) request, (HttpServletResponse) response, filterChain);
    }

    private void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        String requestMethod = request.getMethod();

        if (!requestUri.equals("/api/logout") || !requestMethod.equals("POST")) {
            filterChain.doFilter(request, response);
            return;
        }

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
            sendJsonError(response, 400, "Refresh_400", "Refresh 토큰이 쿠키에 없습니다.", requestUri);
            return;
        }

        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            sendJsonError(response, 401, "Refresh_401", "Refresh 토큰이 만료되었습니다.", requestUri);
            return;
        }

        if (!"refresh".equals(jwtUtil.getCategory(refresh))) {
            sendJsonError(response, 400, "Refresh_400", "유효한 Refresh 토큰이 아닙니다.", requestUri);
            return;
        }

        if (!refreshRepository.existsByRefresh(refresh)) {
            sendJsonError(response, 400, "Refresh_400", "이미 로그아웃된 사용자입니다.", requestUri);
            return;
        }

        // 정상 로그아웃
        refreshRepository.deleteByRefresh(refresh);

        // 쿠키 삭제
        Cookie deleteCookie = new Cookie("refresh", null);
        deleteCookie.setMaxAge(0);
        deleteCookie.setHttpOnly(true);
        deleteCookie.setPath("/");

        // 성공 응답
        SuccessResponse<Object> successResponse = SuccessResponse.success(200, Map.of("message", "성공적으로 로그아웃되었습니다."));

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(successResponse));
    }

    private void sendJsonError(HttpServletResponse response, int status, String code, String reason, String path)
            throws IOException {

        ErrorResponse errorResponse = ErrorResponse.of(status, code, reason, path);

        response.setStatus(status);
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
    }
}
