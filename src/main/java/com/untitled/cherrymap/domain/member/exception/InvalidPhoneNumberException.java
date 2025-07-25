package com.untitled.cherrymap.domain.member.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class InvalidPhoneNumberException extends CherrymapCodeException {
    public static final InvalidPhoneNumberException EXCEPTION = new InvalidPhoneNumberException();

    private InvalidPhoneNumberException() {
        super(MemberErrorCode.INVALID_PHONE_NUMBER_FORMAT);
    }
} 