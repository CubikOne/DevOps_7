package com.s21.devops.sample.bookingservice.Communication;

import com.s21.devops.sample.bookingservice.Communication.Aux.Location;

import java.util.UUID;

public class HotelInfoRes {
    private UUID hotelUid;
    private String name;
    private Location location;
    private Float cost;

    public UUID getHotelUid() {
        return hotelUid;
    }

    public void setHotelUid(UUID hotelUid) {
        this.hotelUid = hotelUid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Float getCost() {
        return cost;
    }

    public void setCost(Float cost) {
        this.cost = cost;
    }
}
