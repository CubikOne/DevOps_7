package com.s21.devops.sample.bookingservice.Communication;

import com.s21.devops.sample.bookingservice.Communication.Aux.DateInterval;
import com.s21.devops.sample.bookingservice.Model.Reservation;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public class BookHotelReq {
    @NotBlank
    private UUID hotelUid;
    @NotBlank
    private Integer room;
    private String paymentInfo;
    private UUID userUid;
    private DateInterval dateInterval;
    private Float roomCost;

    public static Reservation reservationFromBookHotelReq(BookHotelReq bookHotelReq, LocalDate date){
        Reservation reservation = new Reservation();
        reservation.setDate(date);
        reservation.setHotelUid(bookHotelReq.getHotelUid());
        reservation.setUserUid(bookHotelReq.getUserUid());
        reservation.setReservationUid(UUID.randomUUID());
        return reservation;
    }


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

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
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
}
