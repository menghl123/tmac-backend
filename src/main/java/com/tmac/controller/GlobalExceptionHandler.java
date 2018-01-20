package com.tmac.controller;

import com.tmac.exception.ValidateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private final MessageSource messageSource;

    @Autowired
    public GlobalExceptionHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler({
            ValidateException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity validationExceptionHandler(Exception exception) {
        final String message = messageSource
                .getMessage(exception.getMessage(), null, exception.getMessage(), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }
}
