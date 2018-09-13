package com.deezer.security.impl;

import com.deezer.entity.User;
import com.deezer.security.SecurityService;
import com.deezer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultSecurityService implements SecurityService {
    @Autowired
    private UserService userService;

    public void add(User user) {
        String salt = UUID.randomUUID().toString();
        String password = DigestUtils.sha1Hex(user.getPassword() + salt);
        user.setSalt(salt);
        user.setPassword(password);
        userService.add(user);
    }

    public Optional<User> authenticate(String login, String password) {
        Optional<User> optionalUser = userService.get(login);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String expectedPassword = DigestUtils.sha1Hex(password + user.getSalt());
            if (user.getPassword().equals(expectedPassword)) {
                user.setPassword(null);
                user.setSalt(null);
                return optionalUser;
            } else
                return Optional.empty();
        }
        return optionalUser;
    }
}