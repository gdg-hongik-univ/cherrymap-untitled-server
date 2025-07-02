package com.untitled.cherrymap.common.exception;

import com.untitled.cherrymap.common.dto.ErrorReason;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CherrymapCodeException extends RuntimeException {

  private BaseErrorCode errorCode;
  public ErrorReason getErrorReason() {
    return this.errorCode.getErrorReason();
  }
}
