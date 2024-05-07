package com.s21.devops.sample.bookingservice.Controller;

import com.s21.devops.sample.bookingservice.Communication.ErrorResponse;
import com.s21.devops.sample.bookingservice.Communication.ErrorValidationResponse;
import com.s21.devops.sample.bookingservice.Exception.*;
import io.jsonwebtoken.ExpiredJwtException;
import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@ControllerAdvice
public class ExceptionController {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorValidationResponse invalidArgumentExceptionHandle(MethodArgumentNotValidException exception){
        List<FieldError> errors = exception.getBindingResult().getFieldErrors();
        Map<String, String> mapped_errors = new HashMap<>();
        errors.forEach((e) -> mapped_errors.put(e.getField(), "Couldn't find the field '" + e.getField() + "'!"));
        return new ErrorValidationResponse("Validation Error!", mapped_errors);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundExceptionHandle(NotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse expJwtExceptionHandle(ExpiredJwtException exception){
        return new ErrorResponse("Unauthorized action!");
    }

    @ExceptionHandler(InvalidKeySpecException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse invalidKeySpecExceptionHandle(InvalidKeySpecException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ResourceAccessException.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorResponse resourceAccessExceptionHandle(ResourceAccessException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse usernameNotFoundExceptionHandle(UsernameNotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ReservationAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse ReservationAlreadyExistsExceptionHandle(ReservationAlreadyExistsException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse ReservationNotFoundExceptionHandle(ReservationNotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(HotelNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse HotelNotFoundExceptionHandle(HotelNotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse paymentNotFoundExceptionHandle(PaymentNotFoundException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(NoPaymentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse NoPaymentExceptionHandle(NoPaymentException exception){
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(CoudntPayException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse CoudntPayExceptionHandle(CoudntPayException exception){
        return new ErrorResponse(exception.getMessage());
    }


    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse serverErrorExceptionHandle(RuntimeException e){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return new ErrorResponse(e.getMessage() + sw.toString());
    }
}

