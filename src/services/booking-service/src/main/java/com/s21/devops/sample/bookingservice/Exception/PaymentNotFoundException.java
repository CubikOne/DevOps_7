package com.s21.devops.sample.bookingservice.Exception;

public class PaymentNotFoundException extends Exception {
    public PaymentNotFoundException(String message) {
        super(message);
    }
}
