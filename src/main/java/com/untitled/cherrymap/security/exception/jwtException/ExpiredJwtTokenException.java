package com.untitled.cherrymap.security.exception.jwtException;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class ExpiredJwtTokenException extends CherrymapCodeException {
    public static final ExpiredJwtTokenException EXCEPTION = new ExpiredJwtTokenException();

    private ExpiredJwtTokenException() {
        super(JwtErrorCode.EXPIRED_JWT_TOKEN);
    }
}
