package com.example.gestordegastosfacub.network.responses;

import com.example.gestordegastosfacub.models.User;

public class LoginResponse {
    private User user;
    private String error;

    public User getUser() { return user; }

    public String getError() { return error; }
}
