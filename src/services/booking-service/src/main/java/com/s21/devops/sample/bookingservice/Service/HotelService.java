package com.s21.devops.sample.bookingservice.Service;

import com.s21.devops.sample.bookingservice.Communication.HotelCapacityRes;
import com.s21.devops.sample.bookingservice.Communication.HotelInfoRes;
import com.s21.devops.sample.bookingservice.Exception.CustomJwtException;
import com.s21.devops.sample.bookingservice.Exception.CustomRuntimeException;
import com.s21.devops.sample.bookingservice.Exception.HotelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface HotelService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public HotelInfoRes getHotel(UUID hotelUid)
            throws HotelNotFoundException, CustomJwtException, CustomRuntimeException;
    public HotelCapacityRes getHotelCapacity(UUID hotelUid)
            throws HotelNotFoundException, CustomJwtException, CustomRuntimeException;
}
