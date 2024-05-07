package com.s21.devops.sample.paymentsession.Service;

import org.springframework.stereotype.Service;

@Service
public interface OuterPaymentService {
    public Boolean pay(String paymentInfo);
}
