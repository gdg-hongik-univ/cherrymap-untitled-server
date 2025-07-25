package com.untitled.cherrymap.domain.route.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class RouteNotFoundException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new RouteNotFoundException();

    private RouteNotFoundException() {
        super(RouteErrorCode.ROUTE_ERROR_CODE);
    }
}
