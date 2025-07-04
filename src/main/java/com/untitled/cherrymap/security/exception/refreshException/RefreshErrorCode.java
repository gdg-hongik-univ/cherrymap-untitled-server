package com.untitled.cherrymap.security.exception.refreshException;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefreshErrorCode implements BaseErrorCode {
    INVALID_REFRESH_TOKEN(400, "REFRESH_001", "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(400, "REFRESH_002", "만료된 리프레시 토큰입니다.");

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

