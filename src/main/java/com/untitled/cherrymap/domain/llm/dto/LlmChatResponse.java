package com.untitled.cherrymap.domain.llm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "LLM 챗봇 응답")
public record LlmChatResponse(
        @Schema(description = "챗봇의 답변 메시지", example = "걱정하지 마세요! 현재 위치에서 가장 가까운 지하철역을 찾아드릴게요.")
        String response,
        
        @Schema(description = "사용된 AI 모델명", example = "gemini-1.5-flash")
        String model,
        
        @Schema(description = "제안하는 액션 타입", example = "find_nearby_station")
        @JsonProperty("action_type")
        String actionType,
        
        @Schema(description = "응답의 신뢰도 점수 (0.0 ~ 1.0)", example = "0.95")
        @JsonProperty("confidence_score")
        Double confidenceScore
) {} 