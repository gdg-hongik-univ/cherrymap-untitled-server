package com.untitled.cherrymap.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "정류장 정보")
public class Station {

    @Schema(description = "정류장(역) 이름", example = "강남역")
    private String stationName;

    public Station(String stationName) {
        this.stationName = stationName;
    }

    public String getStationName() {
        return stationName;
    }
}
