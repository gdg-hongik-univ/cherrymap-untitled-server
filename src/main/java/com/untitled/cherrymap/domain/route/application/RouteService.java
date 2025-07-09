package com.untitled.cherrymap.domain.route.application;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.route.dao.RouteRepository;
import com.untitled.cherrymap.domain.route.domain.Route;
import com.untitled.cherrymap.domain.route.dto.RouteResponse;
import com.untitled.cherrymap.domain.route.dto.RouteSaveRequest;
import com.untitled.cherrymap.domain.route.exception.DuplicateRouteException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;

    // 경로 저장
    public void saveRoute(Member member, RouteSaveRequest request) {
        // 이미 동일한 endName 경로가 존재하면 저장 금지
        if (routeRepository.existsByMemberAndEndName(member, request.getEndName())) {
            throw DuplicateRouteException.EXCEPTION;
        }

        Route route = Route.builder()
                .member(member)
                .routeName(request.getRouteName())
                .mode(request.getMode())
                .endName(request.getEndName())
                .endLat(request.getEndLat())
                .endLng(request.getEndLng())
                .createdAt(LocalDateTime.now())
                .build();

        routeRepository.save(route);
    }

    // 사용자 전체 경로 조회
    public List<RouteResponse> getRoutes(Member member) {
        return routeRepository.findAllByMemberId(member.getId())
                .stream()
                .map(route -> new RouteResponse(
                        route.getId(),
                        (route.getRouteName() != null && !route.getRouteName().isBlank())
                                ? route.getRouteName()
                                : route.getEndName(),
                        route.getMode()
                ))
                .toList();
    }

    // 특정 경로 상세 조회
    public RouteResponse getRoute(Member member, Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("해당 경로가 존재하지 않습니다."));
        // 본인 소유 검증
        validateOwnership(route, member);
        return toResponse(route);
    }

    // 경로 삭제
    public void deleteRoute(Member member, Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new RuntimeException("해당 경로가 존재하지 않습니다."));
        // 본인 소유 검증
        validateOwnership(route, member);
        routeRepository.delete(route);
    }

    // 경로 DTO 변환
    private RouteResponse toResponse(Route route) {
        String displayName = (route.getRouteName() != null && !route.getRouteName().isBlank())
                ? route.getRouteName()
                : route.getEndName();

        return new RouteResponse(
                route.getId(),
                displayName,
                route.getMode()
        );
    }

    // 소유자 검증
    private void validateOwnership(Route route, Member member) {
        if (!route.getMember().getId().equals(member.getId())) {
            throw new RuntimeException("해당 경로에 접근 권한이 없습니다.");
        }
    }
}
