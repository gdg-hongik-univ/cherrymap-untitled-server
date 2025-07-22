package com.untitled.cherrymap.domain.route.dto;

import com.untitled.cherrymap.domain.route.domain.RouteMode;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "저장된 경로 상세 정보 응답 DTO")
public record RouteResponse(

        @Schema(description = "경로 ID", example = "1")
        Long routeId,

        @Schema(description = "경로명", example = "집에서 학교")
        String routeName,

        @Schema(description = "이동 수단", example = "도보")
        RouteMode mode,

        @Schema(description = "도착지 이름", example = "홍익대학교")
        String endName,

        @Schema(description = "도착지 위도", example = "37.5512293")
        Double endLat,

        @Schema(description = "도착지 경도", example = "126.9241334")
        Double endLng
) {
    @Builder
    public RouteResponse {}
}

