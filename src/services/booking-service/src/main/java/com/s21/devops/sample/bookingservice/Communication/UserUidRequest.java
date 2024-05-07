package com.s21.devops.sample.bookingservice.Communication;

import java.util.UUID;

public class UserUidRequest {
    private UUID userUid;

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }
}
