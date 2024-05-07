package com.s21.devops.sample.bookingservice.Communication.Aux;

public class DayAvailability {
    private String date;
    private Boolean available;

    public static DayAvailability dayAvailabilityFromDate(String date, Boolean available){
        DayAvailability dayAvailability = new DayAvailability();
        dayAvailability.setAvailable(available);
        dayAvailability.setDate(date);
        return dayAvailability;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }
}
