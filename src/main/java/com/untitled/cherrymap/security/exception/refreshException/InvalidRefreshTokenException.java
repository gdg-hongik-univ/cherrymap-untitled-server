package com.untitled.cherrymap.security.exception.refreshException;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class InvalidRefreshTokenException extends CherrymapCodeException {
    public static final InvalidRefreshTokenException EXCEPTION = new InvalidRefreshTokenException();

    private InvalidRefreshTokenException() {
        super(RefreshErrorCode.INVALID_REFRESH_TOKEN);
    }
}
