package com.s21.devops.sample.gatewayservice.Communication;

import com.s21.devops.sample.gatewayservice.Communication.Aux.RoomFilling;

public class HotelsFillingStatsRes {
    private Iterable<RoomFilling> roomFillings;

    public Iterable<RoomFilling> getRoomFillings() {
        return roomFillings;
    }

    public void setRoomFillings(Iterable<RoomFilling> roomFillings) {
        this.roomFillings = roomFillings;
    }
}
