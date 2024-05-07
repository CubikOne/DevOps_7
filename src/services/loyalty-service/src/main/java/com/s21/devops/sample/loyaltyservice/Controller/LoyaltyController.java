package com.s21.devops.sample.loyaltyservice.Controller;

import com.s21.devops.sample.loyaltyservice.Communication.ChargeBalanceReq;
import com.s21.devops.sample.loyaltyservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.loyaltyservice.Exception.LoyaltyNotFoundException;
import com.s21.devops.sample.loyaltyservice.Service.LoyaltyService;
import com.s21.devops.sample.loyaltyservice.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/loyalty")
public class LoyaltyController {
    @Autowired
    private SecurityService securityService;

    @Autowired
    private LoyaltyService loyaltyService;

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PostMapping("/{userUid}")
    public void charge(@PathVariable UUID userUid, @RequestBody ChargeBalanceReq chargeBalanceReq) {
        loyaltyService.chargeBalance(userUid, chargeBalanceReq);
    }

    @GetMapping("/{userUid}")
    public LoyaltyBalanceRes getBalance(@PathVariable UUID userUid)
            throws LoyaltyNotFoundException {
        return loyaltyService.getLoyaltyBalance(userUid);
    }

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, EntityNotFoundException {
        String jwtToken = securityService.authorize(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).build();
    }

}
