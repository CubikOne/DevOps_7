package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.ChargeBalanceReq;
import com.s21.devops.sample.bookingservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.bookingservice.Exception.*;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

@Service
public class LoyaltyServiceImplementation implements LoyaltyService {
    private RestTemplate restTemplate;

    @Value("${booking-service.uuid}")
    private String bookingServiceUUID;

    @Value("${loyalty-service.url}")
    private String loyaltyServiceUrl;

    private String token;

    @Override
    public void auth()
            throws CustomJwtException, CustomRuntimeException {
        String url = loyaltyServiceUrl + "/authorize";
        Base64.Encoder encoder = Base64.getEncoder();
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", "Basic " +
                            encoder.encodeToString(bookingServiceUUID.getBytes()));
                    return execution.execute(request, body);
                })).build();
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            token = response.getHeaders().get("Authorization").get(0);

        }
        catch (HttpClientErrorException ex)
        {
            throw new CustomJwtException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackGetLoyaltyBalance")
    public LoyaltyBalanceRes getLoyaltyBalance(UUID userUid)
            throws LoyaltyNotFoundException, CustomJwtException, CustomRuntimeException {
        String url = loyaltyServiceUrl + "/" + userUid;
        LoyaltyBalanceRes response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, LoyaltyBalanceRes.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getLoyaltyBalance(userUid);
        } catch (HttpClientErrorException ex)
        {
            throw new LoyaltyNotFoundException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return response;
    }

    @Override
    @HystrixCommand(fallbackMethod = "fallbackChargeBalance")
    public void chargeBalance(UUID userUid, ChargeBalanceReq chargeBalanceReq)
            throws CustomJwtException, CustomRuntimeException {
        String url = loyaltyServiceUrl + "/" + userUid;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            restTemplate.postForEntity(url, chargeBalanceReq, String.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex) {
            auth();
            chargeBalance(userUid, chargeBalanceReq);
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    private LoyaltyBalanceRes fallbackGetLoyaltyBalance(UUID userUid){
        return LoyaltyBalanceRes.LoyaltyBalanceResFromParams("NO", 0.0);
    }

    private void fallbackChargeBalance(UUID userUid, ChargeBalanceReq chargeBalanceReq){
        // queue
    }
}
