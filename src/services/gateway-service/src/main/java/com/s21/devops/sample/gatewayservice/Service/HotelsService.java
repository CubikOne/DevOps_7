package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.CreateHotelReq;
import com.s21.devops.sample.gatewayservice.Communication.HotelInfoRes;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.gatewayservice.Exception.HotelAlreadyExistsException;
import com.s21.devops.sample.gatewayservice.Exception.HotelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface HotelsService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public HotelInfoRes[] getAllHotels()
            throws CustomJwtException, CustomRuntimeException;
    public HotelInfoRes getHotel(UUID hotelUid)
            throws HotelNotFoundException, CustomJwtException, CustomRuntimeException;
    public UUID createHotel(CreateHotelReq createHotelReq)
            throws HotelAlreadyExistsException, CustomJwtException, CustomRuntimeException;
}
