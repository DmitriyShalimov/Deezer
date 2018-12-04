package com.deezer.service.security;

import com.deezer.entity.User;
import com.deezer.service.security.entity.UserToken;

import java.util.Optional;

public interface SecurityService {
    Optional<User> getUserByToken(String userTokenHeader);

    UserToken authenticate(String name, String password);

    UserToken register(User user);

    void logout(String token);
}
