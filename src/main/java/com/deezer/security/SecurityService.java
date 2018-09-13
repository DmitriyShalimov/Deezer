package com.deezer.security;

import com.deezer.entity.User;

public interface SecurityService {
    User authenticate(String name, String password);

    void add(User user);
}
