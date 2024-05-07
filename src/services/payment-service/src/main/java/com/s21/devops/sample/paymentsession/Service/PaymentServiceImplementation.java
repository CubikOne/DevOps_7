package com.s21.devops.sample.paymentsession.Service;

import com.s21.devops.sample.paymentsession.Communication.PayReq;
import com.s21.devops.sample.paymentsession.Exception.PaymentNotFoundException;
import com.s21.devops.sample.paymentsession.Model.Payment;
import com.s21.devops.sample.paymentsession.Model.PaymentStatus;
import com.s21.devops.sample.paymentsession.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.UUID;

@Service
public class PaymentServiceImplementation implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OuterPaymentService outerPaymentServiceService;

    @Override
    public UUID pay(PayReq payReq) {
        Payment payment = new Payment();
        payment.setCost(payReq.getCost());
        payment.setPaymentStatus(PaymentStatus.NEW);
        payment.setPaymentUid(UUID.randomUUID());
        payment.setPaymentInfo(payReq.getPaymentInfo());

        if (outerPaymentServiceService.pay(payReq.getPaymentInfo())) {
            payment.setPaymentStatus(PaymentStatus.PAID);
        } else {
            payment.setPaymentStatus(PaymentStatus.CANCELED);
            return null;
        }

        paymentRepository.save(payment);
        return payment.getPaymentUid();
    }

    @Transactional
    @Override
    public void delete(UUID refundUid)
            throws PaymentNotFoundException {
        paymentRepository.deleteByPaymentUid(refundUid);
    }
}
