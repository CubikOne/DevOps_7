package com.s21.devops.sample.gatewayservice.Communication;

import java.util.UUID;

public class UserUidRes {
    private UUID userUid;
    private String role;

    public UserUidRes(){
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