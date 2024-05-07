package com.s21.devops.sample.sessionservice.Service;

import com.s21.devops.sample.sessionservice.Communication.UserUidRes;
import com.s21.devops.sample.sessionservice.Exception.CustomJwtException;
import com.s21.devops.sample.sessionservice.Exception.RoleNotFoundException;
import com.s21.devops.sample.sessionservice.Model.User;
import com.s21.devops.sample.sessionservice.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.UUID;

@Service
public class SessionServiceImplementation implements SessionService{
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Override
    public String authorize(final String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, RoleNotFoundException {
        String encLoginAndPassword = authorization.replace("Basic ", "");
        Base64.Decoder dec = Base64.getDecoder();
        byte[] decbytes = dec.decode(encLoginAndPassword.getBytes());
        String decLoginAndPassword = new String(decbytes);
        String[] loginAndPassword = decLoginAndPassword.split(":", 2);
        User user = userService.findByLoginAndPassword(loginAndPassword[0], loginAndPassword[1]);
        return jwtProvider.generateToken(user.getUserUid());
    }

    @Override
    public UserUidRes validate(String authorization)
            throws NoSuchAlgorithmException, InvalidKeySpecException, CustomJwtException {
        String token = authorization.replace("Bearer ", "");
        jwtProvider.validateToken(token);
        UUID userUid = jwtProvider.getUserUidFromToken(token);
        User user = userService.findByUid(userUid);
        return new UserUidRes(user.getUserUid(), user.getRole().getRole());
    }

    @Override
    public String refresh(String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        String oldToken = authorization.replace("Bearer ", "");
        return jwtProvider.generateToken(jwtProvider.getUserUidFromToken(oldToken));
    }
}
