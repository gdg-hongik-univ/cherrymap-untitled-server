package com.untitled.cherrymap.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Schema(description = "비상 연락처 추가/변경/수정 시 RequestBody DTO(JSON)")
public class PhoneNumberRequest {

    @NotNull(message = "전화번호는 반드시 포함되어야 합니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "비상연락처는 xxx-xxxx-xxxx 형식이어야 합니다.")
    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;
}
