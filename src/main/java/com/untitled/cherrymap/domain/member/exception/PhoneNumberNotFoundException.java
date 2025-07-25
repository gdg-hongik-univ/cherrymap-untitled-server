package com.untitled.cherrymap.domain.member.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class PhoneNumberNotFoundException extends CherrymapCodeException {
    public static final PhoneNumberNotFoundException EXCEPTION = new PhoneNumberNotFoundException();

    private PhoneNumberNotFoundException() {
        super(MemberErrorCode.PHONE_NUMBER_NOT_FOUND);
    }
} 