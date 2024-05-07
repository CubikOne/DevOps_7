package com.s21.devops.sample.gatewayservice.Communication;

import com.s21.devops.sample.gatewayservice.Communication.Aux.DateInterval;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class BookHotelReq {
    @NotBlank
    private UUID hotelUid;
    @NotBlank
    private Integer room;
    @NotBlank
    private String paymentInfo;

    private UUID userUid;
    private DateInterval dateInterval;
    private Float roomCost;

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public DateInterval getDateInterval() {
        return dateInterval;
    }

    public void setDateInterval(DateInterval dateInterval) {
        this.dateInterval = dateInterval;
    }

    public Float getRoomCost() {
        return roomCost;
    }

    public void setRoomCost(Float roomCost) {
        this.roomCost = roomCost;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }
}
