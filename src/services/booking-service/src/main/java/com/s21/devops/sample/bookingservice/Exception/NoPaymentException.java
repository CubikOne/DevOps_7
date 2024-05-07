package com.s21.devops.sample.bookingservice.Exception;

public class NoPaymentException extends Exception {
    public NoPaymentException(String message) {
        super(message);
    }
}
