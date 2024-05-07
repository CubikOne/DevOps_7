package com.s21.devops.sample.gatewayservice.Exception;

public class CustomJwtException extends Exception {
    public CustomJwtException(String message) {
        super(message);
    }
}
