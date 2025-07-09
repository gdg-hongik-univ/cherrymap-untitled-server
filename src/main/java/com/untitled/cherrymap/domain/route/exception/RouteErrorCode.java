package com.untitled.cherrymap.domain.route.exception;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.untitled.cherrymap.common.consts.CherrymapStatic.*;

@Getter
@AllArgsConstructor
public enum RouteErrorCode implements BaseErrorCode {
    DUPLICATE_ROUTE(CONFLICT, "Route_409", "이미 존재하는 경로입니다.");

    private final Integer status;
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