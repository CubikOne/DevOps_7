package com.s21.devops.sample.gatewayservice.Exception;

public class ReservationAlreadyExistsException extends Exception {
    public ReservationAlreadyExistsException(String message) {
        super(message);
    }
}
