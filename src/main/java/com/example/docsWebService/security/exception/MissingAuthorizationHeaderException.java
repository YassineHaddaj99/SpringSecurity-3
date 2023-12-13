package com.example.docsWebService.security.exception;

public class MissingAuthorizationHeaderException extends RuntimeException {

    public MissingAuthorizationHeaderException(String message) {
        super(message);
    }
}
