package com.s21.devops.sample.paymentsession.Service;

import com.s21.devops.sample.paymentsession.Communication.PayReq;
import com.s21.devops.sample.paymentsession.Exception.PaymentNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface PaymentService {
    public UUID pay(PayReq payReq);
    public void delete(UUID refundReq) throws PaymentNotFoundException;
}
