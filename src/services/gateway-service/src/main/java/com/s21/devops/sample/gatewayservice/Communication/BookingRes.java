package com.s21.devops.sample.gatewayservice.Communication;

import com.s21.devops.sample.gatewayservice.Communication.Aux.DateInterval;

import java.util.Date;

public class BookingRes {
    private String hotelName;
    private String hotelUid;
    private String paymentInfo;
    private DateInterval date;

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(String hotelUid) {
        this.hotelUid = hotelUid;
    }

    public String getPaymentInfo() {
        return paymentInfo;
    }

    public void setPaymentInfo(String paymentInfo) {
        this.paymentInfo = paymentInfo;
    }

    public DateInterval getDate() {
        return date;
    }

    public void setDate(DateInterval date) {
        this.date = date;
    }
}
