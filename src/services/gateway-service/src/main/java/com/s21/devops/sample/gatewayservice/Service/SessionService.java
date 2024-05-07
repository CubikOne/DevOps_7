package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.CreateUserReq;
import com.s21.devops.sample.gatewayservice.Communication.UserUidRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface SessionService {

    public UserUidRes validateToken(String token)
            throws CustomJwtException, CustomRuntimeException;

    public void createUser(CreateUserReq createUserReq, String authorization) throws UserAlreadyExistsException;
}
