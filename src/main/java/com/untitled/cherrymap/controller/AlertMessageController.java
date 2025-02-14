package com.untitled.cherrymap.controller;

import com.untitled.cherrymap.domain.RouteRequest;
import com.untitled.cherrymap.service.AlertMessageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

@RestController
public class AlertMessageController {

    private final AlertMessageService alertMessageService;

    public AlertMessageController(AlertMessageService alertMessageService) {
        this.alertMessageService = alertMessageService;
    }

    @GetMapping("/status")
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
