package com.untitled.cherrymap.domain.route.api;

import com.untitled.cherrymap.domain.route.domain.Route;
import com.untitled.cherrymap.domain.route.dto.RouteCreateRequest;
import com.untitled.cherrymap.domain.route.application.RouteService;
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
@RequestMapping("/api/{Id}/routes")
@Tag(name="Route-Controller",description = "경로 관리 API")
public class RouteController {

    private final RouteService routeService;

    // 경로 추가
    @Operation(summary = "경로 추가", description = "member_Id에 해당하는 유저의 새로운 경로 저장")
    @PostMapping("")
    public ResponseEntity<Void> createRoute(@PathVariable("providerId") Long id,
                                            @RequestBody @Valid RouteCreateRequest request) {
        Long routeId = routeService.createRoute(request, id);
        return ResponseEntity.created(URI.create("routes/"+routeId)).build();
    }

    // 저장된 모든 경로 탐색
    @Operation(summary = "저장된 모든 경로 요청", description = "providerId에 해당하는 유저의 모든 저장 경로 리턴.")
    @GetMapping("/list")
    public ResponseEntity<List<Route>> getAllRoute(@PathVariable Long id) {
        // Service 계층에서 이미 처리된 결과를 반환받음
        List<Route> routeList = routeService.getAllRoute(id);
        return ResponseEntity.ok(routeList);
    }

    // 특정 저장 경로 탐색
    @Operation(summary = "특정 저장 경로 요청", description = "routeId에 해당하는 경로 정보 리턴.")
    @GetMapping("/{routeId}")
    public ResponseEntity<Route> getOneRoute(@PathVariable Long id, @PathVariable Long routeId) {
        Route route = routeService.getOneRoute(id, routeId);
        System.out.println("getOneRoute 호출됨: providerId = " + id + ", routeId = " + routeId);
        return ResponseEntity.ok(route);
    }

    // 경로 삭제
    @Operation(summary = "경로 삭제", description = "routeId에 해당하는 경로 삭제.")
    @DeleteMapping("/{routeId}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id, @PathVariable Long routeId) {
        routeService.deleteRoute(id, routeId);
        return ResponseEntity.noContent().build();
    }
}
