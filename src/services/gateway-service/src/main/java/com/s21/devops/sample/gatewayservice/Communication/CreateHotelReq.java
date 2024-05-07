package com.s21.devops.sample.gatewayservice.Communication;

import com.s21.devops.sample.gatewayservice.Communication.Aux.Location;

import javax.validation.constraints.NotBlank;

public class CreateHotelReq {
    @NotBlank
    private String name;
    @NotBlank
    private Location location;

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
}
