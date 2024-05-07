package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.CreateHotelReq;
import com.s21.devops.sample.gatewayservice.Communication.HotelInfoRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.HotelAlreadyExistsException;
import com.s21.devops.sample.gatewayservice.Exception.HotelNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

@Service
public class HotelsServiceImplementation implements HotelsService {
    private RestTemplate restTemplate;

    @Value("${gateway-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${hotel-service.url}")
    //private String hotelServiceUrl = "http://" + System.getenv("HOTEL_SERVICE_HOST") + ":" + System.getenv("HOTEL_SERVICE_PORT") + "/api/v1/hotels";
    private String hotelServiceUrl;

    private String token;

    @Override
    public void auth()
            throws CustomJwtException, CustomRuntimeException {
        String url = hotelServiceUrl + "/authorize";
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
    public HotelInfoRes[] getAllHotels()
            throws CustomJwtException, CustomRuntimeException {
        String url = hotelServiceUrl;
        HotelInfoRes[] response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, HotelInfoRes[].class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getAllHotels();
        }
        catch (HttpClientErrorException ex)
        {
            throw new CustomJwtException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return response;
    }

    @Override
    public HotelInfoRes getHotel(UUID hotelUid)
            throws HotelNotFoundException, CustomJwtException, CustomRuntimeException {
        String url = hotelServiceUrl + "/" + hotelUid.toString();
        HotelInfoRes response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, HotelInfoRes.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getHotel(hotelUid);
        } catch (HttpClientErrorException ex)
        {
            throw new HotelNotFoundException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return response;
    }

    @Override
    public UUID createHotel(CreateHotelReq createHotelReq)
            throws HotelAlreadyExistsException, CustomJwtException, CustomRuntimeException {
        String url = hotelServiceUrl;
        UUID uuid;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, createHotelReq, String.class);
            String location = response.getHeaders().get("Location").get(0);
            System.out.println(location);
            System.out.println(location.substring(location.lastIndexOf('/') + 1));
            uuid = UUID.fromString(location.substring(location.lastIndexOf('/') + 1));
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return createHotel(createHotelReq);
        } catch (HttpClientErrorException ex)
        {
            throw new HotelAlreadyExistsException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
        return uuid;
    }
}
