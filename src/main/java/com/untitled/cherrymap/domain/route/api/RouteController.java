package com.untitled.cherrymap.domain.route.api;

import com.untitled.cherrymap.common.dto.SuccessResponse;
import com.untitled.cherrymap.domain.route.application.RouteService;
import com.untitled.cherrymap.domain.route.dto.RouteResponse;
import com.untitled.cherrymap.domain.route.dto.RouteSaveRequest;
import com.untitled.cherrymap.security.dto.CustomUserDetailsDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/routes")
@Tag(name = "Route API", description = "저장된 경로 관련 API")
public class RouteController {

    private final RouteService routeService;

    // 경로 저장
    @PostMapping
    @Operation(summary = "경로 저장", description = "도착지 정보와 이동 수단을 저장합니다.")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> saveRoute(
            @AuthenticationPrincipal CustomUserDetailsDTO user,
            @Valid @RequestBody RouteSaveRequest request) {

        routeService.saveRoute(user.getMember(), request);
        return ResponseEntity.ok(
                SuccessResponse.success(200, Map.of(
                        "message", "경로가 저장되었습니다."
                ))
        );
    }

    // 사용자 저장 경로 목록 조회
    @GetMapping("/list")
    @Operation(summary = "저장된 경로 목록 조회", description = "로그인한 사용자의 경로 목록을 반환합니다.")
    public ResponseEntity<SuccessResponse<List<RouteResponse>>> getRoutes(
            @AuthenticationPrincipal CustomUserDetailsDTO user) {

        List<RouteResponse> result = routeService.getRoutes(user.getMember());
        return ResponseEntity.ok(
                SuccessResponse.success(200, result)
        );
    }

    // 개별 경로 상세 조회 (본인 소유만 가능)
    @GetMapping("/{routeId}")
    @Operation(summary = "저장된 경로 상세 조회", description = "해당 경로의 상세 정보를 조회합니다.")
    public ResponseEntity<SuccessResponse<RouteResponse>> getRoute(
            @AuthenticationPrincipal CustomUserDetailsDTO user,
            @PathVariable Long routeId) {

        RouteResponse response = routeService.getRoute(user.getMember(), routeId);
        return ResponseEntity.ok(
                SuccessResponse.success(200, response)
        );
    }

    // 경로 삭제 (본인 소유만 가능)
    @DeleteMapping("/{routeId}")
    @Operation(summary = "경로 삭제", description = "해당 경로를 삭제합니다.")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> deleteRoute(
            @AuthenticationPrincipal CustomUserDetailsDTO user,
            @PathVariable Long routeId) {

        routeService.deleteRoute(user.getMember(), routeId);
        return ResponseEntity.ok(
                SuccessResponse.success(200, Map.of(
                        "message", "경로가 삭제되었습니다."
                ))
        );
    }
}
