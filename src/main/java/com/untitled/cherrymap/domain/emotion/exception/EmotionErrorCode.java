package com.untitled.cherrymap.domain.emotion.exception;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.untitled.cherrymap.common.consts.CherrymapStatic.*;

@Getter
@AllArgsConstructor
public enum EmotionErrorCode implements BaseErrorCode {

    EMOTION_ERROR_CODE(NOT_FOUND, "Emotion_404", "해당 감정 기록을 찾을 수 없습니다.");

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