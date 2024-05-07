package com.s21.devops.sample.gatewayservice.Communication;

public class ErrorResponse {
    private String message;

    public ErrorResponse(String m){
        message = m;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
