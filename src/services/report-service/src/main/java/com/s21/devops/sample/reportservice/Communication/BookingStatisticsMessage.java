package com.s21.devops.sample.reportservice.Communication;

import com.s21.devops.sample.reportservice.Model.BookingStatistics;

import java.util.UUID;

public class BookingStatisticsMessage {
    private String timestamp;
    private String date;
    private UUID userUid;
    private UUID hotelUid;
    private UUID reservationUid;
    private Boolean reserved;

    public static BookingStatistics bookingStatisticsFromMessage(BookingStatisticsMessage bookingStatisticsMessage) {
        BookingStatistics bookingStatistics = new BookingStatistics();
        bookingStatistics.setDate(bookingStatisticsMessage.getDate());
        bookingStatistics.setHotelUid(bookingStatisticsMessage.getHotelUid());
        bookingStatistics.setReservationUid(bookingStatisticsMessage.getReservationUid());
        bookingStatistics.setTimestamp(bookingStatisticsMessage.getTimestamp());
        bookingStatistics.setUserUid(bookingStatisticsMessage.getUserUid());
        return bookingStatistics;
    }

    public static BookingStatisticsMessage bookingStatisticsMessageToMessage(BookingStatistics bookingStatistics) {
        BookingStatisticsMessage bookingStatisticsMessage = new BookingStatisticsMessage();
        bookingStatisticsMessage.date = bookingStatistics.getDate();
        bookingStatisticsMessage.timestamp = bookingStatistics.getTimestamp();
        bookingStatisticsMessage.hotelUid = bookingStatistics.getHotelUid();
        bookingStatisticsMessage.userUid = bookingStatistics.getUserUid();
        bookingStatisticsMessage.reservationUid = bookingStatistics.getReservationUid();
        bookingStatisticsMessage.reserved = true;
        return bookingStatisticsMessage;
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

    public Boolean getReserved() {
        return reserved;
    }

    public void setReserved(Boolean reserved) {
        this.reserved = reserved;
    }
}
