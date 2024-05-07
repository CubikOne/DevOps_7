package com.s21.devops.sample.gatewayservice.Communication;

import java.util.UUID;

public class UserInfoRes {
    private String login;
    private UUID useruid;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UUID getUseruid() {
        return useruid;
    }

    public void setUseruid(UUID useruid) {
        this.useruid = useruid;
    }
}
