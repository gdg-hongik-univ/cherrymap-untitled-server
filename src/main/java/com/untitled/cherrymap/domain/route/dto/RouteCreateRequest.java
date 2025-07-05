/*
package com.untitled.cherrymap.domain.route.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;


@Getter
@Schema(description = "경로 저장 시 RequestBody DTO(JSON)")
public class RouteCreateRequest {

    @NotNull(message = "경로명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "경로명은 20자를 넘기지 마시오.")
    @Schema(description = "경로 이름", example = "홍익대학교")
    private String routeName;

    @NotNull(message = "모드(도보 or 대중교통)은 반드시 포함되어야 합니다.")
    @Length(max = 10, message = "모드는 10자를 넘기지 마시오.")
    @Schema(description = "경로 유형: 도보 or 대중교통", example = "대중교통")
    private String mode;

    @NotNull(message = "출발지명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "출발지명은 20자를 넘기지 마시오.")
    @Schema(description = "출발지 이름", example = "강남역")
    private String startName;

    @NotNull(message = "시작 지점 위도는 반드시 포함되어야 합니다.")
    @Schema(description = "출발지 위도", example = "37.4979")
    private double startLat;

    @NotNull(message = "시작 지점 경도는 반드시 포함되어야 합니다.")
    @Schema(description = "출발지 경도", example = "127.0276")
    private double startLng;

    @NotNull(message = "목적지명은 반드시 포함되어야 합니다.")
    @Length(max = 20, message = "목적지명은 20자를 넘기지 마시오.")
    @Schema(description = "도착지 이름", example = "서울역")
    private String endName;

    @NotNull(message = "도착 지점 위도는 반드시 포함되어야 합니다.")
    @Schema(description = "도착지 위도", example = "37.5547")
    private double endLat;

    @NotNull(message = "도착 지점 경도는 반드시 포함되어야 합니다.")
    @Schema(description = "도착지 경도", example = "126.9706")
    private double endLng;
}
*/
