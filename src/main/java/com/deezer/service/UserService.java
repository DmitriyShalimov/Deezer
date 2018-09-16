package com.deezer.service;

import com.deezer.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> get(String login);

    void add(User user);

    boolean isLoginUnique(String login);
}
