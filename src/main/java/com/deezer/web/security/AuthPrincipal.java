package com.deezer.web.security;

import com.deezer.entity.User;

import java.security.Principal;

public class AuthPrincipal implements Principal {
    private User user;

    public AuthPrincipal(User user) {
        this.user = user;
    }

    @Override
    public String getName() {
        return user.getLogin();
    }

    public User getUser() {
        return user;
    }
}
