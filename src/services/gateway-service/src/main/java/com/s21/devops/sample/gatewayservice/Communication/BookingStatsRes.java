package com.s21.devops.sample.gatewayservice.Communication;

public class BookingStatsRes {
    private Iterable<BookingInfo> bookingInfos;

    public Iterable<BookingInfo> getBookingInfos() {
        return bookingInfos;
    }

    public void setBookingInfos(Iterable<BookingInfo> bookingInfos) {
        this.bookingInfos = bookingInfos;
    }
}
