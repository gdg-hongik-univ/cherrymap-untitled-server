package com.untitled.cherrymap.domain.llm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "LLM 챗봇 요청")
public record LlmChatRequest(
        @Schema(description = "사용자의 질문이나 도움 요청 메시지", example = "길을 잃었어요")
        @NotBlank(message = "메시지는 필수입니다")
        @Size(max = 1000, message = "메시지는 1000자를 초과할 수 없습니다")
        String message,
        
        @Schema(description = "사용자의 현재 위치 정보")
        @NotNull(message = "위치 정보는 필수입니다")
        LocationInfo location,
        
        @Schema(description = "목적지 주소 (선택사항)", example = "서울시 강남구 테헤란로 123")
        @JsonProperty("destination_address")
        String destinationAddress,
        
        @Schema(description = "이동 수단 (도보, 대중교통)", example = "대중교통")
        String mode,
        
        @Schema(description = "사용자 상황 설명 (선택사항)", example = "지하철을 놓쳤어요")
        @JsonProperty("user_context")
        String userContext
) {
    @Schema(description = "위치 정보")
    public record LocationInfo(
            @Schema(description = "위도", example = "37.5665")
            @NotNull(message = "위도는 필수입니다")
            Double latitude,
            
            @Schema(description = "경도", example = "126.9780")
            @NotNull(message = "경도는 필수입니다")
            Double longitude
    ) {}
} 