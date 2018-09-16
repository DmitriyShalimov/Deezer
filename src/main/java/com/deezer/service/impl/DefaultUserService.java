package com.deezer.service.impl;

import com.deezer.dao.UserDao;
import com.deezer.entity.User;
import com.deezer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DefaultUserService implements UserService {
    private final UserDao userDao;

    @Autowired
    public DefaultUserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add(User user) {
        userDao.add(user);
    }

    @Override
    public boolean isLoginUnique(String login) {
        Optional<User> user = get(login);
        return !user.isPresent();
    }

    public Optional<User> get(String login) {
        return userDao.get(login);
    }
}