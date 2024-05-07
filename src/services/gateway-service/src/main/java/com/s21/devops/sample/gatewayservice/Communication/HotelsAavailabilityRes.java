package com.s21.devops.sample.gatewayservice.Communication;

import com.s21.devops.sample.gatewayservice.Communication.Aux.DayAvailability;

import java.util.ArrayList;

public class HotelsAavailabilityRes {
    private Iterable<DayAvailability> dayAvailabilities;

    public static HotelsAavailabilityRes hotelsAavailabilityResFromArray(ArrayList<DayAvailability> dayAvailabilities) {
        HotelsAavailabilityRes hotelsAavailabilityRes = new HotelsAavailabilityRes();
        hotelsAavailabilityRes.setDayAvailabilities(dayAvailabilities);
        return hotelsAavailabilityRes;
    }

    public Iterable<DayAvailability> getDayAvailabilities() {
        return dayAvailabilities;
    }

    public void setDayAvailabilities(Iterable<DayAvailability> dayAvailabilities) {
        this.dayAvailabilities = dayAvailabilities;
    }
}
