package com.s21.devops.sample.bookingservice.Model;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reservation", schema = "public")
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservation_id;

    @Column(name = "hotel_uid", nullable = false)
    private UUID hotelUid;

    @Column(name = "user_uid")
    private UUID userUid;

    @Column(name = "payment_uid")
    private UUID paymentUid;

    @Column(name = "reservation_uid", nullable = false, unique = true)
    private UUID reservationUid;

    @Column(name = "cost")
    private Float cost;

    @Column(name = "date")
    private LocalDate date;

    public Integer getReservation_id() {
        return reservation_id;
    }

    public void setReservation_id(Integer reservation_id) {
        this.reservation_id = reservation_id;
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

    public UUID getPaymentUid() {
        return paymentUid;
    }

    public void setPaymentUid(UUID paymentUid) {
        this.paymentUid = paymentUid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UUID getReservationUid() {
        return reservationUid;
    }

    public void setReservationUid(UUID reservationUid) {
        this.reservationUid = reservationUid;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
