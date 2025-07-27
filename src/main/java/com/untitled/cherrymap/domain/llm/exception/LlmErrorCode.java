package com.untitled.cherrymap.domain.llm.exception;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum LlmErrorCode implements BaseErrorCode {
    LLM_COMMUNICATION_ERROR(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "LLM_COMMUNICATION_ERROR",
            "LLM 서버와의 통신 중 오류가 발생했습니다."
    );

    private final int status;
    private final String code;
    private final String reason;

    @Override
    public ErrorReason getErrorReason() {
        return ErrorReason.builder()
                .status(this.status)
                .code(this.code)
                .reason(this.reason)
                .build();
    }
} 