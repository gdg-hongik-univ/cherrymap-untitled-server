package com.untitled.cherrymap.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "비상연락처 등록/변경 시 요청 DTO (RequestBody)")
public class PhoneNumberRequest {

    @NotNull(message = "전화번호는 반드시 포함되어야 합니다.")
    @Pattern(regexp = "^(\\d{3}-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})$", 
             message = "비상연락처는 xxx-xxxx-xxxx(휴대폰) 또는 xxx-xxx-xxxx(유선전화) 형식이어야 합니다.")
    @Schema(description = "비상연락처", example = "010-1234-5678", 
            allowableValues = {"휴대폰: xxx-xxxx-xxxx", "유선전화: xxx-xxx-xxxx"})
    private String phoneNumber;
}
