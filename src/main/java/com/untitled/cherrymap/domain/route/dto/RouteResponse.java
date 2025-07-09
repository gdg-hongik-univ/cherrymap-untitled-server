package com.untitled.cherrymap.domain.route.dto;

import com.untitled.cherrymap.domain.route.domain.RouteMode;

public record RouteResponse(
        Long id,                  // route ID
        String displayName,       // 사용자 지정 이름 or 도착지 이름
        RouteMode mode          // 이동 수단
) {}
