package com.s21.devops.sample.hotelservice.Communication;

import com.s21.devops.sample.hotelservice.Communication.Aux.Location;
import com.s21.devops.sample.hotelservice.Model.Hotel;

import java.util.UUID;

public class HotelInfoRes {
    private UUID hotelUid;
    private String name;
    private Location location;
    private Float cost;

    public static HotelInfoRes hotelInfoResFromHotel(Hotel hotel) {
        HotelInfoRes hotelInfoRes = new HotelInfoRes();
        Location location = new Location();
        location.setAddress(hotel.getAddress());
        location.setCity(hotel.getCity().getCity());
        location.setCountry(hotel.getCity().getCountry().getCountry());
        hotelInfoRes.setHotelUid(hotel.getHotelUid());
        hotelInfoRes.setName(hotel.getName());
        hotelInfoRes.setLocation(location);
        hotelInfoRes.setCost(hotel.getCost());
        return hotelInfoRes;
    }

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
