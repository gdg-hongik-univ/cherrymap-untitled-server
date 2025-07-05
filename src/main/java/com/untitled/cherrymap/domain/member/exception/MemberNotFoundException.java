package com.untitled.cherrymap.domain.member.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class MemberNotFoundException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new MemberNotFoundException();

    private MemberNotFoundException() {
        super(MemberErrorCode.MEMBER_NOT_FOUND);
    }
}

