package com.example.docsWebService.security.exception;

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message) {
        super(message);
    }
}
