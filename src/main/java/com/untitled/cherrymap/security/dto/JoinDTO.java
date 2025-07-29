package com.untitled.cherrymap.security.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
@Schema(description = "회원가입 요청 DTO")
public class JoinDTO {
    
    @NotNull(message = "닉네임은 필수입니다.")
    @Schema(description = "닉네임", example = "cherry_user")
    private String nickname;
    
    @NotNull(message = "아이디는 필수입니다.")
    @Schema(description = "아이디", example = "user123")
    private String username;
    
    @NotNull(message = "비밀번호는 필수입니다.")
    @Schema(description = "비밀번호", example = "password123")
    private String password;
    
    @NotNull(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^(\\d{3}-\\d{4}-\\d{4}|\\d{3}-\\d{3}-\\d{4})$", 
             message = "전화번호는 xxx-xxxx-xxxx(휴대폰) 또는 xxx-xxx-xxxx(유선전화) 형식이어야 합니다.")
    @Schema(description = "전화번호", example = "010-1234-5678", 
            allowableValues = {"휴대폰: xxx-xxxx-xxxx", "유선전화: xxx-xxx-xxxx"})
    private String phoneNumber;
}

