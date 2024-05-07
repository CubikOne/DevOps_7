package com.s21.devops.sample.hotelservice.Service;

import com.s21.devops.sample.hotelservice.Communication.CreateHotelReq;
import com.s21.devops.sample.hotelservice.Communication.HotelCapacityRes;
import com.s21.devops.sample.hotelservice.Communication.HotelInfoRes;
import com.s21.devops.sample.hotelservice.Exception.HotelAlreadyExistsException;
import com.s21.devops.sample.hotelservice.Exception.HotelNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface HotelService {
    public Iterable<HotelInfoRes> getAllHotels();
    public HotelInfoRes getHotel(UUID hotelUid) throws HotelNotFoundException;
    public UUID createHotel(CreateHotelReq createHotelReq) throws HotelAlreadyExistsException;
    public HotelCapacityRes getHotelCapacity(UUID hotelUid) throws HotelNotFoundException;
}
