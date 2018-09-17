package com.deezer.dao;

import com.deezer.entity.User;

import java.util.Optional;

public interface UserDao {
    void add(User user);

    Optional<User> getByLogin(String login);
}
