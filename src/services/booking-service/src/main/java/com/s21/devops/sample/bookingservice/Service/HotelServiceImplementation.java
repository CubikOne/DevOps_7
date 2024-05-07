package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.HotelCapacityRes;
import com.s21.devops.sample.bookingservice.Communication.HotelInfoRes;
import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import com.s21.devops.sample.bookingservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.bookingservice.Exception.HotelNotFoundException;
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
public class HotelServiceImplementation implements HotelService {
    private RestTemplate restTemplate;

    @Value("${booking-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${hotel-service.url}")
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
    @HystrixCommand(fallbackMethod = "fallbackGetHotel")
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
    @HystrixCommand(fallbackMethod = "fallbackGetHotelCapacity")
    public HotelCapacityRes getHotelCapacity(UUID hotelUid)
            throws HotelNotFoundException, CustomJwtException, CustomRuntimeException {
            String url = hotelServiceUrl + "/" + hotelUid.toString() + "/rooms";
            HotelCapacityRes response;
            restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                    .add((request, body, execution) -> {
                        request.getHeaders().add("Authorization", token);
                        return execution.execute(request, body);
                    })).build();
            try {
                response = restTemplate.getForObject(url, HotelCapacityRes.class);
            }
            catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
            {
                auth();
                return getHotelCapacity(hotelUid);
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

    private HotelInfoRes fallbackGetHotel(UUID hotelUid){
        return new HotelInfoRes();
    }

    private HotelCapacityRes fallbackGetHotelCapacity(UUID hotelUid){
        return new HotelCapacityRes();
    }
}
