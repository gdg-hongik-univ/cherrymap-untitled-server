package com.untitled.cherrymap.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;


@Getter
public class RouteCreateRequest {

    @NotNull(message = "경로명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "경로명은 20자를 넘기지 마시오.")
    private String routeName;

    @NotNull(message = "출발지명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "출발지명은 20자를 넘기지 마시오.")
    private String startName;

    @NotNull(message = "시작 지점 위도는 반드시 포함되어야 합니다.")
    private double startLat;

    @NotNull(message = "시작 지점 경도는 반드시 포함되어야 합니다.")
    private double startLng;

    @NotNull(message = "목적지명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "목적지명은 20자를 넘기지 마시오.")
    private String endName;

    @NotNull(message = "도착 지점 위도는 반드시 포함되어야 합니다.")
    private double endLat;

    @NotNull(message = "도착 지점 경도는 반드시 포함되어야 합니다.")
    private double endLng;
}
