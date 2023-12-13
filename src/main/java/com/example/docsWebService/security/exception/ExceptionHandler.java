package com.example.docsWebService.security.exception;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.util.Map;

@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<Object> handleAuthenticationFailedException(AuthenticationFailedException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("message", e.getMessage()));
    }

    @org.springframework.web.bind.annotation.ExceptionHandler({ExpiredJwtException.class, JwtException.class})
    public ResponseEntity<Object> handleJwtExceptions(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
    }
}
