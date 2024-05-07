package com.s21.devops.sample.sessionservice.Communication;

import java.util.UUID;

public class UserUidRes {
    private UUID userUid;
    private String role;

    public UserUidRes(UUID userUid, String role){
        this.userUid = userUid;
        this.role = role;
    }

    public UUID getUserUid() {
        return userUid;
    }

    public void setUserUid(UUID userUid) {
        this.userUid = userUid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
