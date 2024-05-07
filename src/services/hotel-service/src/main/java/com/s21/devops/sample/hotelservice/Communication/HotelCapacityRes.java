package com.s21.devops.sample.hotelservice.Communication;

import com.s21.devops.sample.hotelservice.Model.Hotel;

public class HotelCapacityRes {
    private Integer rooms;

    public Integer getRooms() {
        return rooms;
    }

    public void setRooms(Integer rooms) {
        this.rooms = rooms;
    }

    public static HotelCapacityRes hotelCapacityResFromHotel(Hotel hotel){
        HotelCapacityRes hotelCapacityRes = new HotelCapacityRes();
        hotelCapacityRes.setRooms(hotel.getRooms());
        return hotelCapacityRes;
    }
}
