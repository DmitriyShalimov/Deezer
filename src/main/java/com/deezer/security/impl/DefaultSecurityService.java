package com.deezer.security.impl;

import com.deezer.entity.User;
import com.deezer.security.SecurityService;
import com.deezer.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultSecurityService implements SecurityService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;

    @Autowired
    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    public boolean register(User user) {
        boolean loginUnique = userService.isLoginUnique(user.getLogin());
        if (loginUnique) {
            String salt = UUID.randomUUID().toString();
            String password = DigestUtils.sha1Hex(user.getPassword() + salt);
            user.setSalt(salt);
            user.setPassword(password);
            userService.add(user);
        }
        return loginUnique;
    }

    public Optional<User> authenticate(String login, String password) {
        Optional<User> optionalUser = userService.get(login);
        return optionalUser.map(user -> checkPassword(user, password));
    }

    private User checkPassword(User user, String password) {
        String expectedPassword = DigestUtils.sha1Hex(password + user.getSalt());
        if (user.getPassword().equals(expectedPassword)) {
            user.setPassword(null);
            user.setSalt(null);
            return user;
        } else {
            logger.info("User {} credentials are invalid", user.getLogin());
            return null;
        }
    }
}