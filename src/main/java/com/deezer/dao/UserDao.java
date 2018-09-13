package com.deezer.dao;

import com.deezer.entity.User;

public interface UserDao {
    void add(User user);

    User get(String login);
}
