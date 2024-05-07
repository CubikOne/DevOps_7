package com.s21.devops.sample.loyaltyservice.Security;

import com.s21.devops.sample.loyaltyservice.Exception.CustomJwtException;
import com.s21.devops.sample.loyaltyservice.Model.ServiceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

import static org.springframework.util.StringUtils.hasText;

@Component
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZATION = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String token = getTokenFromRequest((HttpServletRequest) servletRequest);
        try {
            if (token != null && jwtProvider.validateToken(token)) {
                String serviceUid = jwtProvider.getServiceUidFromToken(token).toString();
                ServiceDetails serviceDetails = ServiceDetails.fromUserEntityToCustomServiceDetails(UUID.fromString(serviceUid));
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(serviceDetails,
                        null, serviceDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("No such algorithm");
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("Invalid key spec ");
        } catch (CustomJwtException e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String bearer = request.getHeader(AUTHORIZATION);
        if (hasText(bearer) && bearer.startsWith("Bearer ")) {
            return bearer.substring(7);
        }
        return null;
    }
}