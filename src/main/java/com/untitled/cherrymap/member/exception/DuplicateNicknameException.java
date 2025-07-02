package com.untitled.cherrymap.member.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class DuplicateNicknameException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new DuplicateNicknameException();

    private DuplicateNicknameException() {
        super(MemberErrorCode.DUPLICATE_NICKNAME);
    }
}

