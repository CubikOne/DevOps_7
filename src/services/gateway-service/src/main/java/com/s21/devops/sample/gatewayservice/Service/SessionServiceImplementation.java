package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.CreateUserReq;
import com.s21.devops.sample.gatewayservice.Communication.UserUidRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class SessionServiceImplementation implements SessionService {
    @Value("${session-service.url}")
    //private String sessionServiceUrl = "http://" + System.getenv("SESSION_SERVICE_HOST") + ":" + System.getenv("SESSION_SERVICE_PORT") + "/api/v1/auth";
    private String sessionServiceUrl;

    @Override
    public UserUidRes validateToken(String token) throws CustomJwtException, CustomRuntimeException {
        String url = sessionServiceUrl + "/validate";
        System.out.println(url);
        RestTemplate restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            UserUidRes response
                    = restTemplate.getForObject(url, UserUidRes.class);
            return response;
        }
        catch (HttpClientErrorException.Unauthorized ex)
        {
            throw new CustomJwtException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void createUser(CreateUserReq createUserReq, String authorization) throws UserAlreadyExistsException {
        String url = sessionServiceUrl + "/users";
        RestTemplate restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors().add((request, body, execution) ->
        {
            request.getHeaders().add("Authorization", authorization);
            return execution.execute(request, body);
        })).build();
        try {
            restTemplate.postForLocation(url, createUserReq, CreateUserReq.class);
        }
        catch (HttpClientErrorException ex)
        {
            throw new UserAlreadyExistsException(ex.getLocalizedMessage().replace("\\\"400 : [{\\\"message\\\":\\\"", "").replace("\\\"}]", ""));
        }
    }
}
