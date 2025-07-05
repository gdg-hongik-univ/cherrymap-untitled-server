/*
package com.untitled.cherrymap.domain.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

@Schema(description = "경로 요청 데이터")
public class RouteRequest {

    @Schema(description = "대중교통 모드",example = "subway")
    private String mode;

    @Schema(description = "사용자의 현재 위치", example = "서울역")
    private String userLocation;

    @Schema(description = "출발 위치", example = "강남역")
    private String startLocation;

    @Schema(description = "도착 위치", example = "서울역")
    private String endLocation;

    @Schema(description = "경유지 리스트 (역 또는 정류장 이름 리스트)", example = "[\"강남역\", \"교대역\", \"서울역\"]")
    private List<String> stationList;

    public RouteRequest() {}

    public RouteRequest(String mode, String userLocation, String startLocation, String endLocation, List<String> stationList) {
        this.mode = mode;
        this.userLocation = userLocation;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stationList = stationList;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(String userLocation) {
        this.userLocation = userLocation;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public List<String> getStationList() {
        return stationList;
    }

    public void setStationList(List<String> stationList) {
        this.stationList = stationList;
    }
}
*/
