package com.s21.devops.sample.bookingservice.Exception;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException(String message) {
        super(message);
    }
}
