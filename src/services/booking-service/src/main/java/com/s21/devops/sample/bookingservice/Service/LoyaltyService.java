package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.ChargeBalanceReq;
import com.s21.devops.sample.bookingservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import com.s21.devops.sample.bookingservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.bookingservice.Exception.LoyaltyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface LoyaltyService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public LoyaltyBalanceRes getLoyaltyBalance(UUID userUid)
            throws LoyaltyNotFoundException, CustomJwtException, CustomRuntimeException;
    public void chargeBalance(UUID userUid, ChargeBalanceReq chargeBalanceReq) throws CustomJwtException, CustomRuntimeException;
}
