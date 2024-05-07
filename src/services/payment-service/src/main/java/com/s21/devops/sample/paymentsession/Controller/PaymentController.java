package com.s21.devops.sample.paymentsession.Controller;

import com.s21.devops.sample.paymentsession.Communication.PayReq;
import com.s21.devops.sample.paymentsession.Exception.NoPaymentException;
import com.s21.devops.sample.paymentsession.Exception.PaymentNotFoundException;
import com.s21.devops.sample.paymentsession.Service.PaymentService;
import com.s21.devops.sample.paymentsession.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private SecurityService securityService;

    @PostMapping("")
    public ResponseEntity<Void> pay(@Valid @RequestBody PayReq payReq)
            throws NoPaymentException {
        UUID paymentUid = paymentService.pay(payReq);
        if (paymentUid != null) {
            System.out.println("payed");
            return ResponseEntity.created(ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{paymentUid}")
                    .buildAndExpand(paymentUid)
                    .toUri()
            ).build();
        } else {
            throw new NoPaymentException("Could not pay with such payment info!");
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{paymentUid}")
    public void refund(@PathVariable UUID paymentUid)
            throws PaymentNotFoundException {
        System.out.println("refunded");
        paymentService.delete(paymentUid);
    }

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, EntityNotFoundException {
        String jwtToken = securityService.authorize(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).build();
    }

}
