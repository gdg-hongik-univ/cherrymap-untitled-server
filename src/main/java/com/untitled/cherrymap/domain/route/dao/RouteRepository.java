package com.untitled.cherrymap.domain.route.dao;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.route.domain.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRepository extends JpaRepository<Route, Long> {
    // 특정 회원이 저장한 모든 경로 목록 조회
    List<Route> findAllByMemberId(Long memberId);

    boolean existsByMemberAndEndName(Member member, String endName);

}
