package com.glovo.controller.api;

import com.glovo.exception.GlovoDaoException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ IllegalArgumentException.class })
    protected ResponseEntity<Object> handleIllegalArgument(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Can't save empty object",
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({ GlovoDaoException.class })
    protected ResponseEntity<Object> handleGlovoDao(
            Exception ex, WebRequest request) {
        return handleExceptionInternal(ex, "Internal error",
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

}
