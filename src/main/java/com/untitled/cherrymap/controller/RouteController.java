package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.Route;
import com.untitled.cherrymap.dto.RouteCreateRequest;
import com.untitled.cherrymap.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/{providerId}/routes")
@Tag(name="Route-Controller",description = "경로 관리 API")
public class RouteController {

    private final RouteService routeService;

    // 경로 추가
    @Operation(summary = "경로 추가", description = "providerId에 해당하는 유저의 새로운 경로 저장")
    @PostMapping("")
    public ResponseEntity<Void> createRoute(@PathVariable("providerId") String providerId,
                                            @RequestBody @Valid RouteCreateRequest request) {
        Long routeId = routeService.createRoute(request, providerId);
        return ResponseEntity.created(URI.create("routes/"+routeId)).build();
    }

    // 저장된 모든 경로 탐색
    @Operation(summary = "저장된 모든 경로 요청", description = "providerId에 해당하는 유저의 모든 저장 경로 리턴.")
    @GetMapping("/list")
    public ResponseEntity<List<Route>> getAllRoute(@PathVariable String providerId) {
        // Service 계층에서 이미 처리된 결과를 반환받음
        List<Route> routeList = routeService.getAllRoute(providerId);
        return ResponseEntity.ok(routeList);
    }

    // 특정 저장 경로 탐색
    @Operation(summary = "특정 저장 경로 요청", description = "routeId에 해당하는 경로 정보 리턴.")
    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getOneRoute(@PathVariable String providerId, @PathVariable Long routeId) {
        Route route = routeService.getOneRoute(providerId, routeId);
        System.out.println("getOneRoute 호출됨: providerId = " + providerId + ", routeId = " + routeId);
        return ResponseEntity.ok(route);
    }

    // 경로 삭제
    @Operation(summary = "경로 삭제", description = "routeId에 해당하는 경로 삭제.")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable String providerId, @PathVariable Long routeId) {
        routeService.deleteRoute(providerId, routeId);
        return ResponseEntity.noContent().build();
    }
}
