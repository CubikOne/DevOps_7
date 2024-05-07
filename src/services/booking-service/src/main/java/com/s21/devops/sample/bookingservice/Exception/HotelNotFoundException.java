package com.s21.devops.sample.bookingservice.Exception;

public class HotelNotFoundException extends Exception {
    public HotelNotFoundException(String message) {
        super(message);
    }
}
