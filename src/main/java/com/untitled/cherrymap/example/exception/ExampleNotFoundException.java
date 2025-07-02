package com.untitled.cherrymap.example.exception;

import com.untitled.cherrymap.common.exception.CherrymapCodeException;

public class ExampleNotFoundException extends CherrymapCodeException {
    public static final CherrymapCodeException EXCEPTION = new ExampleNotFoundException();

    private ExampleNotFoundException() {
        super(ExampleErrorCode.EXAMPLE_ERROR_CODE);
    }
}

