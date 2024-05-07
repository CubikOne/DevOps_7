package com.s21.devops.sample.loyaltyservice.Model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "balances", schema = "public")
public class LoyaltyBalance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer loyalty_id;

    @Column(name = "user_uid")
    private UUID userUid;

    @Column(name = "balance")
    private Float balance;

    public static LoyaltyBalance newLoyaltyBalance(UUID userUid) {
        LoyaltyBalance loyaltyBalance = new LoyaltyBalance();
        loyaltyBalance.setBalance(0.0f);
        loyaltyBalance.setUserUid(userUid);
        return loyaltyBalance;
    }

    public Integer getLoyalty_id() {
        return loyalty_id;
    }

    public void setLoyalty_id(Integer loyalty_id) {
        this.loyalty_id = loyalty_id;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }
}
