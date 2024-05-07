package com.s21.devops.sample.reportservice.Model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "booking_statistics", schema = "public")
public class BookingStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer stat_id;

    @Column(name = "timestamp")
    private String timestamp;

    @Column(name = "date")
    private String date;

    @Column(name = "user_uid")
    private UUID userUid;

    @Column(name = "hotel_uid")
    private UUID hotelUid;

    @Column(name = "reservation_uid")
    private UUID reservationUid;

    public Integer getStat_id() {
        return stat_id;
    }

    public void setStat_id(Integer stat_id) {
        this.stat_id = stat_id;
    }

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
}
