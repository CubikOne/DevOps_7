package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.PayReq;
import com.s21.devops.sample.bookingservice.Communication.RefundReq;
import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import com.s21.devops.sample.bookingservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.bookingservice.Exception.NoPaymentException;
import com.s21.devops.sample.bookingservice.Exception.PaymentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PaymentsService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public UUID pay(PayReq payReq)
            throws NoPaymentException, CustomRuntimeException, CustomJwtException;
    public void refund(UUID refundUid)
            throws CustomJwtException, CustomRuntimeException, NoPaymentException, PaymentNotFoundException;
}
