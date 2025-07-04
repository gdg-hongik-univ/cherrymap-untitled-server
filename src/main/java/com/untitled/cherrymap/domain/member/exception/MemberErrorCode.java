package com.untitled.cherrymap.domain.member.exception;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.untitled.cherrymap.common.consts.CherrymapStatic.*;

@Getter
@AllArgsConstructor
public enum MemberErrorCode implements BaseErrorCode {

    MEMBER_NOT_FOUND(NOT_FOUND, "Member_404", "해당 사용자를 찾을 수 없습니다."),
    DUPLICATE_NICKNAME(CONFLICT, "Member_409", "이미 사용 중인 닉네임입니다.");

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