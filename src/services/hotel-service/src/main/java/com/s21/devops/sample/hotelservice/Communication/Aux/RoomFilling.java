package com.s21.devops.sample.hotelservice.Communication.Aux;

public class RoomFilling {
    private Integer number;
    private DateInterval interval;
    private String status;

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public DateInterval getInterval() {
        return interval;
    }

    public void setInterval(DateInterval interval) {
        this.interval = interval;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
