package com.s21.devops.sample.bookingservice.Security;

import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtProvider {

    @Value("${privateKey}")
    private String privateKey;

    @Value("${publicKey}")
    private String publicKey;

    public String generateToken(UUID serviceUid) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Date date = Date.from(LocalDate.now().plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKey.getBytes()));
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return Jwts.builder()
                .setSubject(serviceUid.toString())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.RS256, kf.generatePrivate(spec))
                .compact();
    }

    public boolean validateToken(String token) throws NoSuchAlgorithmException, CustomJwtException {
        String msg;
        byte[] encodedPrivateKey = Base64.decode(publicKey.getBytes());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedPrivateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        try {
            Jwts.parser().setSigningKey(kf.generatePublic(spec)).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException expEx) {
            msg = "Token expired";
        } catch (UnsupportedJwtException unsEx) {
            msg = "Unsupported jwt";
        } catch (MalformedJwtException mjEx) {
            msg = "Malformed jwt";
        } catch (SignatureException sEx) {
            msg = "Invalid signature";
        } catch (Exception e) {
            msg = "invalid token";
        }
        throw new CustomJwtException(msg);
    }

    public UUID getServiceUidFromToken(String token) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] encodedPrivateKey = Base64.decode(publicKey.getBytes());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(encodedPrivateKey);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        Claims claims = Jwts.parser().setSigningKey(kf.generatePublic(spec)).parseClaimsJws(token).getBody();
        return UUID.fromString(claims.getSubject());
    }
}
