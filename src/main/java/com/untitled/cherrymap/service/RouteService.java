package com.untitled.cherrymap.service;

import com.untitled.cherrymap.domain.Member;
import com.untitled.cherrymap.domain.Route;
import com.untitled.cherrymap.dto.RouteCreateRequest;
import com.untitled.cherrymap.exception.BadDataAccessException;
import com.untitled.cherrymap.exception.BadRequestException;
import com.untitled.cherrymap.message.ErrorMessage;
import com.untitled.cherrymap.repository.MemberRepository;
import com.untitled.cherrymap.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final RouteRepository routeRepository;
    private final MemberRepository memberRepository;

    // 경로 생성
    @Transactional
    public Long createRoute(RouteCreateRequest request, String providerId) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }

        // 중복 경로 검사
        boolean exists = routeRepository.existsByRouteDetails(
                request.getMode(),
                request.getStartLat(), request.getStartLng(),
                request.getEndLat(), request.getEndLng()
        );

        if (exists) {
            throw new BadRequestException(ErrorMessage.DUPLICATE_ROUTE);
        }

        Route route = Route.builder().member(member)
                .routeName(request.getRouteName())
                .mode(request.getMode())
                .startName(request.getStartName())
                .startLat(request.getStartLat())
                .startLng(request.getStartLng())
                .endName(request.getEndName())
                .endLat(request.getEndLat())
                .endLng(request.getEndLng())
                .build();
        routeRepository.save(route);

        return route.getId();
    }
    // 전체 경로 조회
    @Transactional(readOnly = true)
    public List<Route> getAllRoute(String providerId) {
        Member member = memberRepository.findByProviderId(providerId);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }
        return routeRepository.findAllByMember(member);
    }

    // 특정 경로 조회
    @Transactional(readOnly = true)
    public Route getOneRoute(String providerId, Long routeId) {
        return validateMemberAndGetRoute(providerId, routeId);
    }

    // 경로 삭제
    @Transactional
    public void deleteRoute(String providerId, Long routeId) {
        Route route = validateMemberAndGetRoute(providerId, routeId);
        routeRepository.delete(route);
    }
    // 유저의 권한 확인 및 특정 경로 탐색
    private Route validateMemberAndGetRoute(String providerId, Long routeId) {
        Member member = memberRepository.findByProviderId(providerId);
        //System.out.println("조회된 Member: " + member);
        if (member == null) {
            throw new BadRequestException(ErrorMessage.MEMBER_NOT_FOUND_WITH + providerId);
        }

        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new BadRequestException(ErrorMessage.ROUTE_NOT_FOUND_WITH + routeId));
        //System.out.println("조회된 Route: " + route);
        if (!member.equals(route.getMember())) {
            throw new BadDataAccessException(ErrorMessage.ACCESS_DENIED);
        }
        return route;
    }

}
