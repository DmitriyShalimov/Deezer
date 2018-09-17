package com.deezer.security;

import com.deezer.entity.User;

import java.util.Optional;

public interface SecurityService {
    Optional<User> authenticate(String name, String password);

    boolean register(User user);
}
