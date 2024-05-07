package com.s21.devops.sample.gatewayservice.Exception;

public class CustomRuntimeException extends Exception {
    public CustomRuntimeException(String message) {
        super(message);
    }
}
