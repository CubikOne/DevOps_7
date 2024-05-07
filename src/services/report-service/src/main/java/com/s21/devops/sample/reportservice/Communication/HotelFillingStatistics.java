package com.s21.devops.sample.reportservice.Communication;

import java.util.UUID;

public class HotelFillingStatistics {
    private Integer roomsFilling;
    private UUID hotelUid;
    private String date;

    public Integer getRoomsFilling() {
        return roomsFilling;
    }

    public void setRoomsFilling(Integer roomsFilling) {
        this.roomsFilling = roomsFilling;
    }

    public void incrementFilling() {
        this.roomsFilling++;
    }

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
