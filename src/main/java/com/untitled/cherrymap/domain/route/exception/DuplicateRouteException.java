package com.untitled.cherrymap.domain.route.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class DuplicateRouteException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new DuplicateRouteException();

    private DuplicateRouteException() {
        super(RouteErrorCode.DUPLICATE_ROUTE);
    }
}
