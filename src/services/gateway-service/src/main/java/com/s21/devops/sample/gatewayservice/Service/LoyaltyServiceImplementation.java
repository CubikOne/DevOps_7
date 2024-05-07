package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.LoyaltyBalanceRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.LoyaltyNotFoundException;
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

    @Value("${gateway-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${loyalty-service.url}")
    //private String loyaltyServiceUrl = "http://" + System.getenv("LOYALTY_SERVICE_HOST") + ":" + System.getenv("LOYALTY_SERVICE_PORT") + "/api/v1/loyalty";
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
                            encoder.encodeToString(gatewayServiceUUID.getBytes()));
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
}
