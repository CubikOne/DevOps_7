package com.s21.devops.sample.gatewayservice.Communication;

import javax.validation.constraints.NotBlank;

public class CreateUserReq {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
