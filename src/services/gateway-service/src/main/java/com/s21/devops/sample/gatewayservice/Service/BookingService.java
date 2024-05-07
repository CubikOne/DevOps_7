package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.BookHotelReq;
import com.s21.devops.sample.gatewayservice.Communication.BookingInfo;
import com.s21.devops.sample.gatewayservice.Communication.HotelsAavailabilityRes;
import com.s21.devops.sample.gatewayservice.Communication.PatchRoomsInfoReq;
import com.s21.devops.sample.gatewayservice.Exception.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BookingService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public void patchRoomInfo(UUID hoteUid, PatchRoomsInfoReq patchRoomsInfoReq) throws CustomJwtException, CustomRuntimeException;
    public void bookHotel(BookHotelReq bookHotelReq)
            throws ReservationAlreadyExistsException, CustomJwtException, CustomRuntimeException, HotelAlreadyExistsException;
    public void removeBooking(UUID hotelUid, UUID userUid) throws CustomJwtException, CustomRuntimeException, ReservationNotFoundException;
    public BookingInfo getBookingInfo(UUID hotelUid, UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, CustomRuntimeException;
    public BookingInfo[] getAllBookingInfo(UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, CustomRuntimeException;
    public HotelsAavailabilityRes getHotelsAvailaibility(UUID hotelUid, String from, String to)
            throws CustomJwtException, HotelNotFoundException, CustomRuntimeException;
}
