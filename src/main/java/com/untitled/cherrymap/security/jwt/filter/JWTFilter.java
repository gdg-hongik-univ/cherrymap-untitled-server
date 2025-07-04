package com.untitled.cherrymap.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untitled.cherrymap.common.dto.ErrorResponse;
import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.exception.MemberNotFoundException;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import com.untitled.cherrymap.domain.member.dao.MemberRepository;
import com.untitled.cherrymap.security.jwt.util.JWTUtil;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;


/**
 * 모든 요청에서 Access 토큰을 검사하고,
 * 유효한 경우 인증 객체를 SecurityContext에 등록
 */
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;
    private final MemberRepository memberRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String accessToken = request.getHeader("access");

        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            jwtUtil.isExpired(accessToken);
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json; charset=UTF-8");
            objectMapper.writeValue(response.getWriter(), ErrorResponse.of(
                    401, "ACCESS_401", "Access 토큰이 만료되었습니다.", request.getRequestURI()
            ));
            return;
        }

        String category = jwtUtil.getCategory(accessToken);
        if (!category.equals("access")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().print("invalid access token");
            return;
        }

        // memberId 기반으로 사용자 조회
        String memberId = jwtUtil.getMemberId(accessToken);

        Member member = memberRepository.findById(Long.parseLong(memberId))
                .orElseThrow(() -> MemberNotFoundException.EXCEPTION);

        CustomUserDetailsDTO customUserDetailsDTO = new CustomUserDetailsDTO(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetailsDTO,
                null,
                customUserDetailsDTO.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
