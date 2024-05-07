package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.BookHotelReq;
import com.s21.devops.sample.gatewayservice.Communication.BookingInfo;
import com.s21.devops.sample.gatewayservice.Communication.HotelsAavailabilityRes;
import com.s21.devops.sample.gatewayservice.Communication.PatchRoomsInfoReq;
import com.s21.devops.sample.gatewayservice.Exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.Base64;
import java.util.UUID;

@Service
public class BookingServiceImplementation implements BookingService {
    private RestTemplate restTemplate;

    @Value("${gateway-service.uuid}")
    private String gatewayServiceUUID;

    @Value("${booking-service.url}")
    //private String bookingServiceUrl = "http://" + System.getenv("BOOKING_SERVICE_HOST") + ":" + System.getenv("BOOKING_SERVICE_PORT") + "/api/v1/booking";
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
    public void patchRoomInfo(UUID hotelUid, PatchRoomsInfoReq patchRoomsInfoReq)
            throws CustomJwtException, CustomRuntimeException {
        String url = bookingServiceUrl + "/" + hotelUid + "/rooms";
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            restTemplate.postForObject(url, patchRoomsInfoReq, String.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            patchRoomInfo(hotelUid, patchRoomsInfoReq);
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void bookHotel(BookHotelReq bookHotelReq)
            throws ReservationAlreadyExistsException, CustomJwtException, CustomRuntimeException {
        String url = bookingServiceUrl;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            restTemplate.postForEntity(url, bookHotelReq, String.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            bookHotel(bookHotelReq);
        } catch (HttpClientErrorException ex)
        {
            throw new ReservationAlreadyExistsException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    public void removeBooking(UUID hotelUid, UUID userUid)
            throws CustomJwtException, CustomRuntimeException, ReservationNotFoundException {
        String url = bookingServiceUrl + "/" + hotelUid + "?userUid=" + userUid;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            restTemplate.delete(url, String.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            removeBooking(hotelUid, userUid);
        } catch (HttpClientErrorException ex)
        {
            throw new ReservationNotFoundException(ex.getMessage());
        }
        catch (Exception ex)
        {
            throw new CustomRuntimeException(ex.getMessage());
        }
    }

    @Override
    public BookingInfo getBookingInfo(UUID hotelUid, UUID userUid) throws ReservationNotFoundException, CustomJwtException, CustomRuntimeException {
        String url = bookingServiceUrl + "/" + hotelUid + "?userUid=" + userUid;
        BookingInfo response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, BookingInfo.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getBookingInfo(hotelUid, userUid);
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
    public BookingInfo[] getAllBookingInfo(UUID userUid) throws CustomJwtException, CustomRuntimeException {
        String url = bookingServiceUrl + "?userUid=" + userUid;
        BookingInfo[] response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, BookingInfo[].class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getAllBookingInfo(userUid);
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
    public HotelsAavailabilityRes getHotelsAvailaibility(UUID hotelUid, String from, String to) throws CustomJwtException, HotelNotFoundException, CustomRuntimeException {
        String url = bookingServiceUrl + "/" + hotelUid + "/rooms?from=" + from + "&to=" + to;
        HotelsAavailabilityRes response;
        restTemplate = new RestTemplateBuilder(rt-> rt.getInterceptors()
                .add((request, body, execution) -> {
                    request.getHeaders().add("Authorization", token);
                    return execution.execute(request, body);
                })).build();
        try {
            response = restTemplate.getForObject(url, HotelsAavailabilityRes.class);
        }
        catch (HttpClientErrorException.Forbidden | HttpClientErrorException.Unauthorized ex)
        {
            auth();
            return getHotelsAvailaibility(hotelUid, from, to);
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
}
