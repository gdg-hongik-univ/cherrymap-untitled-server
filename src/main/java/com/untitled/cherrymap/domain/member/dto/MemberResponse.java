package com.untitled.cherrymap.domain.member.dto;

import com.untitled.cherrymap.domain.member.domain.Member;
import com.untitled.cherrymap.domain.member.domain.UserRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Schema(description = "사용자 정보 응답 DTO")
public record MemberResponse(

        @Schema(description = "사용자 ID", example = "1")
        Long memberId,

        @Schema(description = "유저 닉네임", example = "cherryUser")
        String nickname,

        @Schema(description = "유저 이름", example = "홍길동")
        String username,

        @Schema(description = "역할", example = "ROLE_USER", allowableValues = {"ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR"})
        UserRole role,

        @Schema(description = "비상연락처", example = "010-1234-5678")
        String phoneNumber
) {
    @Builder
    public MemberResponse {}

    public static MemberResponse from(Member member) {
        return MemberResponse.builder()
                .memberId(member.getId())
                .nickname(member.getNickname())
                .username(member.getUsername())
                .role(member.getRole())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
} 