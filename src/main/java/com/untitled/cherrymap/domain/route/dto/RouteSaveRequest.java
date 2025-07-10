package com.untitled.cherrymap.domain.route.dto;

import com.untitled.cherrymap.domain.route.domain.RouteMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Schema(description = "경로 저장 시 요청 DTO (RequestBody)")
public class RouteSaveRequest {
    @Length(max = 20, message = "경로명은 20자를 넘을 수 없습니다.")
    @Schema(description = "사용자가 설정한 경로명 (입력하지 않으면 도착지 이름이 대신 사용됩니다)", example = "집에서 학교")
    private String routeName;

    @NotNull(message = "이동 수단(mode)은 필수입니다. (도보 또는 대중교통)")
    @Schema(description = "이동 수단", example = "도보", allowableValues = {"도보", "대중교통"})
    private RouteMode mode;

    @NotNull(message = "도착지 이름(endName)은 필수입니다.")
    @Length(max = 20, message = "도착지 이름은 20자를 넘을 수 없습니다.")
    @Schema(description = "도착지 이름 (Tmap 검색결과에서 선택된 장소명)", example = "홍익대학교")
    private String endName;

    @NotNull(message = "도착지 위도(endLat)는 필수입니다.")
    @Schema(description = "도착지 위도", example = "37.5512293")
    private Double endLat;

    @NotNull(message = "도착지 경도(endLng)는 필수입니다.")
    @Schema(description = "도착지 경도", example = "126.9241334")
    private Double endLng;
}
