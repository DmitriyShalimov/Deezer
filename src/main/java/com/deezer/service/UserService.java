package com.deezer.service;

import com.deezer.entity.User;

public interface UserService {
    User get(String login);

    void add(User user);
}
