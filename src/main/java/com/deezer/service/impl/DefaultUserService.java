package com.deezer.service.impl;

import com.deezer.dao.UserDao;
import com.deezer.entity.User;
import com.deezer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultUserService implements UserService {
   @Autowired
    private UserDao userDao;

    public void add(User user) {
        userDao.add(user);
    }

    public User get(String login) {
        User user = userDao.get(login);
        return user;
    }
}