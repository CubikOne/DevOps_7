package com.s21.devops.sample.bookingservice.Communication;

import java.util.Map;

public class ErrorValidationResponse {
    private String message;
    private Map<String, String> errors;

    public ErrorValidationResponse(String m, Map<String,String> e){
        message = m;
        errors = e;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }
}
