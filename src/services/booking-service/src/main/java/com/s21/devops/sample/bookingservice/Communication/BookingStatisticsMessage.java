package com.s21.devops.sample.bookingservice.Communication;

import java.util.UUID;

public class BookingStatisticsMessage {
    private String timestamp;
    private String date;
    private UUID userUid;
    private UUID hotelUid;
    private UUID reservationUid;
    private Boolean reserved;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public UUID getReservationUid() {
        return reservationUid;
    }

    public void setReservationUid(UUID reservationUid) {
        this.reservationUid = reservationUid;
    }

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }
}
