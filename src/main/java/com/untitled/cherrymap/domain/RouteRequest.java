package com.untitled.cherrymap.domain;

import java.util.List;

public class RouteRequest {
    private String mode; // "subway" 또는 "bus"
    private String userLocation;
    private String startLocation;
    private String endLocation;
    private List<String> stationList; // 역(정류장) 리스트

    // 기본 생성자 (필수)
    public RouteRequest() {}

    // 모든 필드를 포함하는 생성자
    public RouteRequest(String mode, String userLocation, String startLocation, String endLocation, List<String> stationList) {
        this.mode = mode;
        this.userLocation = userLocation;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.stationList = stationList;
    }

    // Getter & Setter
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


