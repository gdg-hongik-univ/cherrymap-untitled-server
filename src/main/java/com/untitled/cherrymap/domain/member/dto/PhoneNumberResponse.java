package com.untitled.cherrymap.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "비상연락처 응답 DTO")
public record PhoneNumberResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long memberId,

        @Schema(description = "사용자 닉네임", example = "cherryUser")
        String nickname,

        @Schema(description = "비상연락처", example = "010-1234-5678")
        String phoneNumber,

        @Schema(description = "전화번호 타입", example = "휴대폰", allowableValues = {"휴대폰", "유선전화"})
        String phoneType
) {
    @Builder
    public PhoneNumberResponse {}
} 