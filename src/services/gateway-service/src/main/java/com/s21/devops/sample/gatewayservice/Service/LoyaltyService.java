package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.LoyaltyNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface LoyaltyService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public LoyaltyBalanceRes getLoyaltyBalance(UUID userUid)
            throws LoyaltyNotFoundException, CustomJwtException, CustomRuntimeException;
}
