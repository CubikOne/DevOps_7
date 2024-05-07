package com.s21.devops.sample.bookingservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s21.devops.sample.bookingservice.Communication.BookHotelReq;
import com.s21.devops.sample.bookingservice.Communication.BookingInfo;
import com.s21.devops.sample.bookingservice.Communication.HotelsAavailabilityRes;
import com.s21.devops.sample.bookingservice.Communication.PatchRoomsInfoReq;
import com.s21.devops.sample.bookingservice.Exception.*;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface BookingService {
    public void patchHotelInfo(UUID hoteUid, PatchRoomsInfoReq patchRoomsInfoReq) throws JsonProcessingException;
    public void bookHotel(BookHotelReq bookHotelReq)
            throws ReservationAlreadyExistsException, CustomJwtException, CustomRuntimeException, PaymentNotFoundException, NoPaymentException, LoyaltyNotFoundException, JsonProcessingException, CoudntPayException;
    public void removeBooking(UUID hotelUid, UUID userUid)
            throws NoPaymentException, CustomJwtException, CustomRuntimeException, PaymentNotFoundException, JsonProcessingException;
    public BookingInfo getBookingInfo(UUID hotelUid, UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, HotelNotFoundException, CustomRuntimeException;
    public Iterable<BookingInfo> getAllBookingInfo(UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, HotelNotFoundException, CustomRuntimeException;
    public HotelsAavailabilityRes getHotelsAvailaibility(UUID hotelUid, String from, String to)
            throws CustomJwtException, HotelNotFoundException, CustomRuntimeException;
}
