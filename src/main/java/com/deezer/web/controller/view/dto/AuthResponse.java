package com.deezer.web.controller.view.dto;

public class AuthResponse {
    boolean isAuth;

    public AuthResponse(boolean isAuth) {
        this.isAuth = isAuth;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void setAuth(boolean auth) {
        isAuth = auth;
    }
}
