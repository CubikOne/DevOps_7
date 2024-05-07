package com.s21.devops.sample.gatewayservice.Exception;

public class HotelAlreadyExistsException extends Exception {
    public HotelAlreadyExistsException(String message) {
        super(message);
    }
}
