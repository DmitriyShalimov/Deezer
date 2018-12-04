package com.deezer.web.security;

import com.deezer.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class UserRequestWrapper extends HttpServletRequestWrapper {
    private User user;


    public UserRequestWrapper(HttpServletRequest request, User user) {
        super(request);
        this.user = user;
    }

    @Override
    public Principal getUserPrincipal() {
        return new AuthPrincipal(user);
    }
}
