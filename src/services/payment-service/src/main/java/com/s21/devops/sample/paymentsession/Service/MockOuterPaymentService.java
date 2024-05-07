package com.s21.devops.sample.paymentsession.Service;

import org.springframework.stereotype.Service;

@Service
public class MockOuterPaymentService implements OuterPaymentService {
    @Override
    public Boolean pay(String paymentInfo) {
        //Всегда возвращает "успешно"
        return true;
    }
}
