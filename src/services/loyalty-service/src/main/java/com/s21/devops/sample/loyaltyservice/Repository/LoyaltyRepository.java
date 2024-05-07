package com.s21.devops.sample.loyaltyservice.Repository;

import com.s21.devops.sample.loyaltyservice.Model.LoyaltyBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LoyaltyRepository extends JpaRepository<LoyaltyBalance, Long> {
    public Optional<LoyaltyBalance> findByUserUid(UUID userUid);
}
