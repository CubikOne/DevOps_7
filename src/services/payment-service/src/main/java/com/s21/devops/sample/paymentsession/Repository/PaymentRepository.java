package com.s21.devops.sample.paymentsession.Repository;

import com.s21.devops.sample.paymentsession.Model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public Optional<Payment> findByPaymentUid(UUID paymentUid);
    public void deleteByPaymentUid(UUID paymentUid);
}
