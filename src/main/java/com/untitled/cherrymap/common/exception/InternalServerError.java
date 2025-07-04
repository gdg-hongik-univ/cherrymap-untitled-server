package com.untitled.cherrymap.common.exception;

import com.untitled.cherrymap.example.exception.ExampleErrorCode;

public class InternalServerError extends CherrymapCodeException {
  public static final CherrymapCodeException EXCEPTION = new InternalServerError();
  private InternalServerError() {
    super(ExampleErrorCode.EXAMPLE_ERROR_CODE);
  }
}
