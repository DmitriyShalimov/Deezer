package com.deezer.web.controller;

import com.deezer.entity.User;

import javax.servlet.http.HttpSession;

public class Util {
    private static final String LOGGED_USER_KEY = "loggedUser";

    private Util() {
    }

    public static int getUserIdFromHttpSession(HttpSession session) {
        User user = (User) session.getAttribute(LOGGED_USER_KEY);
        return user.getId();
    }
}
