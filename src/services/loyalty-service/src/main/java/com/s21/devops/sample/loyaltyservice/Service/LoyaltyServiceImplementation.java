package com.s21.devops.sample.loyaltyservice.Service;

import com.s21.devops.sample.loyaltyservice.Communication.ChargeBalanceReq;
import com.s21.devops.sample.loyaltyservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.loyaltyservice.Exception.LoyaltyNotFoundException;
import com.s21.devops.sample.loyaltyservice.Model.LoyaltyBalance;
import com.s21.devops.sample.loyaltyservice.Model.LoyaltyStatus;
import com.s21.devops.sample.loyaltyservice.Repository.LoyaltyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LoyaltyServiceImplementation implements LoyaltyService {
    @Autowired
    private LoyaltyRepository loyaltyRepository;

    @Override
    public LoyaltyBalanceRes getLoyaltyBalance(UUID userUid)
            throws LoyaltyNotFoundException {
        LoyaltyBalance loyaltyBalance = loyaltyRepository.findByUserUid(userUid)
                .orElseThrow(() -> new LoyaltyNotFoundException("Loyalty balance for user " + userUid + " was not found!"));
        LoyaltyStatus loyaltyStatus = LoyaltyStatus.NO;
        Double discount = 0.0;

        if (loyaltyBalance.getBalance() > 250000) {
            loyaltyStatus = LoyaltyStatus.GOLD;
            discount = 15.0;
        } else if (loyaltyBalance.getBalance() > 100000) {
            loyaltyStatus = LoyaltyStatus.SILVER;
            discount = 7.5;
        } else if (loyaltyBalance.getBalance() > 50000) {
            loyaltyStatus = LoyaltyStatus.BRONZE;
            discount = 2.5;
        }

        return LoyaltyBalanceRes.LoyaltyBalanceResFromParams(loyaltyStatus.name(), discount);
    }

    @Override
    public void chargeBalance(UUID userUid, ChargeBalanceReq chargeBalanceReq) {
        LoyaltyBalance loyaltyBalance = loyaltyRepository.findByUserUid(userUid).orElse(LoyaltyBalance.newLoyaltyBalance(userUid));
        loyaltyBalance.setBalance(loyaltyBalance.getBalance() + chargeBalanceReq.getCharge());
        loyaltyRepository.save(loyaltyBalance);
    }
}
