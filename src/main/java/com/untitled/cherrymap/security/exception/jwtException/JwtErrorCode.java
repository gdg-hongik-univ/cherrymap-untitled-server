package com.untitled.cherrymap.security.exception.jwtException;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {
    INVALID_JWT_TOKEN(401, "JWT_001", "유효하지 않은 JWT 토큰입니다."),
    EXPIRED_JWT_TOKEN(401, "JWT_002", "만료된 JWT 토큰입니다.");

    private final int status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .reason(reason)
                .code(code)
                .status(status)
                .build();
    }
}
