package com.s21.devops.sample.paymentsession.Service;

import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public interface SecurityService {
    public String authorize(final String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException;
}
