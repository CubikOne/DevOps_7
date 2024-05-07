package com.s21.devops.sample.sessionservice.Service;

import com.s21.devops.sample.sessionservice.Communication.UserUidRes;
import com.s21.devops.sample.sessionservice.Exception.CustomJwtException;
import com.s21.devops.sample.sessionservice.Exception.RoleNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@Service
public interface SessionService {
    public String authorize(final String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, RoleNotFoundException;
    public UserUidRes validate(String authorization)
            throws NoSuchAlgorithmException, InvalidKeySpecException, CustomJwtException;
    public String refresh(String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException;
}
