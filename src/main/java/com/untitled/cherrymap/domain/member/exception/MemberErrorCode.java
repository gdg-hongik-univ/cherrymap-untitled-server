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
    DUPLICATE_NICKNAME(CONFLICT, "Member_409", "이미 존재하는 닉네임입니다."),
    INVALID_PHONE_NUMBER_FORMAT(BAD_REQUEST, "Member_400", "올바르지 않은 전화번호 형식입니다. xxx-xxxx-xxxx(휴대폰) 또는 xxx-xxx-xxxx(유선전화) 형식으로 입력해주세요."),
    PHONE_NUMBER_NOT_FOUND(NOT_FOUND, "Member_404", "등록된 비상연락처가 없습니다.");

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