package com.java.course.auto_catalog.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends ApplicationException {
    public NotFoundException(String message) {
        super(message, null, HttpStatus.NOT_FOUND);
    }

    public NotFoundException(String message, Throwable cause) {
        super(cause, message, HttpStatus.NOT_FOUND);
    }
}