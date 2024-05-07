package com.s21.devops.sample.loyaltyservice.Service;

import com.s21.devops.sample.loyaltyservice.Security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.UUID;

@Service
public class SessionServiceImplementation implements SecurityService{
    @Autowired
    private JwtProvider jwtProvider;

    @Value("${gateway-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${booking-service.uuid}")
    private String bookingServiceUUID;

    @Override
    public String authorize(final String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException {
        String encLogin = authorization.replace("Basic ", "");
        Base64.Decoder dec = Base64.getDecoder();
        byte[] decbytes = dec.decode(encLogin.getBytes());
        String decLogin = new String(decbytes);
        if (!decLogin.equals(gatewayServiceUUID) && !decLogin.equals(bookingServiceUUID)) {
            throw new UsernameNotFoundException("Service " + decLogin + " is not authorized for this operation");
        }
        return jwtProvider.generateToken(UUID.fromString(decLogin));
    }

}
