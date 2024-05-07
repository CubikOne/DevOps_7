package com.s21.devops.sample.bookingservice.Model;

import java.util.UUID;

public class ReservationCount {
    private UUID hotel_uid;
    private Integer total;

    public ReservationCount(UUID hotel_uid, Integer total) {
        this.hotel_uid = hotel_uid;
        this.total = total;
    }

    public UUID getHotel_uid() {
        return hotel_uid;
    }

    public void setHotel_uid(UUID hotel_uid) {
        this.hotel_uid = hotel_uid;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
