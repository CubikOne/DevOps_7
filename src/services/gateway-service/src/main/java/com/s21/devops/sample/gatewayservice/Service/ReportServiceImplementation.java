package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.BookingStatisticsMessage;
import com.s21.devops.sample.gatewayservice.Communication.HotelFillingStatistics;

import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

@Service
public class ReportServiceImplementation implements ReportService {
    private RestTemplate restTemplate;

    @Value("${gateway-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${report-service.url}")
    private String reportServiceUrl;

    private String token;

    @Override
    public void auth()
            throws CustomJwtException, CustomRuntimeException {
        String url = reportServiceUrl + "/authorize";
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
    public BookingStatisticsMessage[] getUserStatistics(String from, String to) throws CustomJwtException, CustomRuntimeException {
        String url = reportServiceUrl + "/users" + "?from=" + from + "&to=" + to;
        BookingStatisticsMessage[] response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, BookingStatisticsMessage[].class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getUserStatistics(from, to);
        } catch (HttpClientErrorException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return response;
    }

    @Override
    public HotelFillingStatistics[] getHotelStatistics(String from, String to) throws CustomJwtException, CustomRuntimeException {
        String url = reportServiceUrl + "/hotels" + "?from=" + from + "&to=" + to;
        HotelFillingStatistics[] response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, HotelFillingStatistics[].class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getHotelStatistics(from, to);
        } catch (HttpClientErrorException ex)
        {
            throw ex;
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return response;
    }
}
