package com.s21.devops.sample.bookingservice.Communication;

import com.s21.devops.sample.bookingservice.Communication.Aux.DateInterval;

import java.time.LocalDate;
import java.util.UUID;

public class BookingInfo {
    private UUID hotelUid;
    private UUID userUid;
    private DateInterval date;
    private String hotel;
    private Float totalCost;

    public static BookingInfo bookingInfoFromFields(UUID hotelUid, UUID userUid, LocalDate from, LocalDate to, String hotel, Float cost){
        BookingInfo bookingInfo = new BookingInfo();
        DateInterval dateInterval = new DateInterval();
        dateInterval.setFrom(from.toString());
        dateInterval.setTo(to.toString());
        bookingInfo.date = dateInterval;
        bookingInfo.hotelUid = hotelUid;
        bookingInfo.userUid = userUid;
        bookingInfo.hotel = hotel;
        bookingInfo.totalCost = cost;
        return bookingInfo;
    }

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    public DateInterval getDate() {
        return date;
    }

    public void setDate(DateInterval date) {
        this.date = date;
    }

    public String getHotel() {
        return hotel;
    }

    public void setHotel(String hotel) {
        this.hotel = hotel;
    }

    public Float getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Float totalCost) {
        this.totalCost = totalCost;
    }
}
