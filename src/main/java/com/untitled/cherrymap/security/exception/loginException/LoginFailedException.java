package com.untitled.cherrymap.security.exception.loginException;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class LoginFailedException extends CherrymapCodeException {
    public static final LoginFailedException EXCEPTION = new LoginFailedException();

    private LoginFailedException() {
        super(LoginErrorCode.LOGIN_FAILED);
    }
}

