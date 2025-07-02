package com.untitled.cherrymap.security.exception.refreshException;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class ExpiredRefreshTokenException extends CherrymapCodeException {
    public static final ExpiredRefreshTokenException EXCEPTION = new ExpiredRefreshTokenException();

    private ExpiredRefreshTokenException() {
        super(RefreshErrorCode.EXPIRED_REFRESH_TOKEN);
    }
}
