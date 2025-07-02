package com.untitled.cherrymap.security.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.untitled.cherrymap.common.dto.ErrorResponse;
import com.untitled.cherrymap.member.Member;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import com.untitled.cherrymap.member.MemberRepository;
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
    private final MemberRepository memberRepository;      // DB에서 사용자 정보 조회용
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 요청 헤더에서 Access 토큰 가져오기
        String accessToken = request.getHeader("access");

        // 토큰 없으면 다음 필터로 넘어가기
        if (accessToken == null) {
            filterChain.doFilter(request, response);
            return; // 필수 종료
        }

        // 토큰 만료 여부 확인 (만료시 다음 필터로 넘기지 않음, 401 응답 반환)
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


        // 해당 토큰이 Access 토큰인지 확인
        String category = jwtUtil.getCategory(accessToken);

        if (!category.equals("access")) {
            PrintWriter writer = response.getWriter();
            writer.print("invalid access token");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        // 유효한 토큰일 경우 사용자 정보 추출
        String nickname = jwtUtil.getNickname(accessToken);

        // DB에서 사용자 정보 조회 (실제 존재하는지 검증)
       Member member = memberRepository.findByNickname(nickname);
        if (member == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // User 정보 기반으로 UserDetails 생성
        CustomUserDetailsDTO customUserDetailsDTO = new CustomUserDetailsDTO(member);

        // Authentication 객체 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetailsDTO, null, customUserDetailsDTO.getAuthorities()
        );

        // 인증 객체를 SecurityContextHolder에 저장 -> 이후 인증 완료 상태로 요청 처리됨
        SecurityContextHolder.getContext().setAuthentication(authToken);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}

