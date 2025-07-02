package com.untitled.cherrymap.security.exception.jwtException;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class InvalidJwtTokenException extends CherrymapCodeException {
    public static final InvalidJwtTokenException EXCEPTION = new InvalidJwtTokenException();

    private InvalidJwtTokenException() {
        super(JwtErrorCode.INVALID_JWT_TOKEN);
    }
}


