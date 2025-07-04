package com.untitled.cherrymap.common.advice;

import com.untitled.cherrymap.common.dto.ErrorReason;
import com.untitled.cherrymap.common.dto.ErrorResponse;
import com.untitled.cherrymap.common.exception.BaseErrorCode;
import com.untitled.cherrymap.common.exception.CherrymapCodeException;
import com.untitled.cherrymap.common.exception.GlobalErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CherrymapCodeException.class)
    public ResponseEntity<ErrorResponse> PromesaCodeExceptionHandler(CherrymapCodeException e, HttpServletRequest request) {
        BaseErrorCode code = e.getErrorCode();
        ErrorReason errorReason = code.getErrorReason();
        ErrorResponse errorResponse = ErrorResponse.from(errorReason, request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(errorReason.getStatus()))
                .body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request)
            throws IOException {
        log.error("INTERNAL SERVER ERROR", e);
        GlobalErrorCode internalServerError = GlobalErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.of(
                internalServerError.getStatus(),
                internalServerError.getCode(),
                internalServerError.getReason(),
                request.getRequestURL().toString()
        );
        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatus()))
                .body(errorResponse);
    }
}
