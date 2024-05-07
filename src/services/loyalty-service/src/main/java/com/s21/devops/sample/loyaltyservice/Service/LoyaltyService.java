package com.s21.devops.sample.loyaltyservice.Service;

import com.s21.devops.sample.loyaltyservice.Communication.ChargeBalanceReq;
import com.s21.devops.sample.loyaltyservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.loyaltyservice.Exception.LoyaltyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface LoyaltyService {
    public LoyaltyBalanceRes getLoyaltyBalance(UUID userUid)
            throws LoyaltyNotFoundException;
    public void chargeBalance(UUID userUid, ChargeBalanceReq chargeBalanceReq);
}
