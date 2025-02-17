package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.RouteRequest;
import com.untitled.cherrymap.service.AlertMessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@Tag(name="AlertMessage-Controller",description = "대중교통 별 알림 메시지 반환 API")
@RestController
public class AlertMessageController {

    private final AlertMessageService alertMessageService;

    public AlertMessageController(AlertMessageService alertMessageService) {
        this.alertMessageService = alertMessageService;
    }

    @Operation(summary = "현재 상태 반영하여 경로 요청", description = "주어진 경로 요청(RouteRequest) 정보를 기반으로 알림 메시지를 반환합니다.")
    @GetMapping("/api/status")
    public ResponseEntity<String> getStatus(@RequestBody RouteRequest routeRequest) {

        return ResponseEntity.ok(alertMessageService.getAlertMessage(
                routeRequest.getMode(),
                routeRequest.getUserLocation(),
                routeRequest.getStartLocation(),
                routeRequest.getEndLocation(),
                routeRequest.getStationList()
        ));

    }
}
