package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.PayReq;
import com.s21.devops.sample.bookingservice.Communication.RefundReq;
import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import com.s21.devops.sample.bookingservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.bookingservice.Exception.NoPaymentException;
import com.s21.devops.sample.bookingservice.Exception.PaymentNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

@Service
public class PaymentsServiceImplementation implements PaymentsService{
    private RestTemplate restTemplate;

    @Value("${booking-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${payment-service.url}")
    private String bookingServiceUrl;

    private String token;

    @Override
    public void auth()
            throws CustomJwtException, CustomRuntimeException {
        String url = bookingServiceUrl + "/authorize";
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
    public UUID pay(PayReq payReq)
            throws NoPaymentException, CustomRuntimeException, CustomJwtException {
        String url = bookingServiceUrl;
        UUID uuid = null;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, payReq, String.class);
            String location = response.getHeaders().get("Location").get(0);
            System.out.println(location);
            System.out.println(location.substring(location.lastIndexOf('/') + 1));
            uuid = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
            return uuid;
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return pay(payReq);
        } catch (HttpClientErrorException ex)
        {
            throw new NoPaymentException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void refund(UUID refundUid)
            throws CustomJwtException, CustomRuntimeException, NoPaymentException, PaymentNotFoundException {
        String url = bookingServiceUrl + "/" + refundUid;
        UUID uuid = null;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            restTemplate.delete(url);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            refund(refundUid);
        } catch (HttpClientErrorException ex)
        {
            throw new PaymentNotFoundException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }
}
