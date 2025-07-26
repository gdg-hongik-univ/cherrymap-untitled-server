package com.untitled.cherrymap.domain.member.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class DuplicateNicknameException extends CherrymapCodeException {
    public static final DuplicateNicknameException EXCEPTION = new DuplicateNicknameException();

    private DuplicateNicknameException() {
        super(MemberErrorCode.DUPLICATE_NICKNAME);
    }
}

