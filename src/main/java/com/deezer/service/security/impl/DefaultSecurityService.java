package com.deezer.service.security.impl;

import com.deezer.entity.User;
import com.deezer.service.security.SecurityService;
import com.deezer.service.UserService;
import com.deezer.service.security.entity.UserToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class DefaultSecurityService implements SecurityService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final UserService userService;
    private final List<UserToken> userTokenList = new CopyOnWriteArrayList<>();


    @Autowired
    public DefaultSecurityService(UserService userService) {
        this.userService = userService;
    }

    public UserToken register(User user) {
        boolean loginUnique = userService.isLoginUnique(user.getLogin());
        if (loginUnique) {
            String salt = UUID.randomUUID().toString();
            String password = DigestUtils.sha1Hex(user.getPassword() + salt);
            user.setSalt(salt);
            user.setPassword(password);
            userService.add(user);
            return getTokenForUser(user);
        } else {
            throw new SecurityException("User with login +" + user.getLogin() + " already exists");
        }
    }

    @Override
    public void logout(String token) {
        userTokenList.removeIf(userToken -> userToken.getUuid().equals(token));
    }

    @Override
    public Optional<User> getUserByToken(String userTokenHeader) {
        Optional<UserToken> userToken = userTokenList.stream()
                .filter(token -> token.getUuid()
                        .equalsIgnoreCase(userTokenHeader))
                .findFirst();
        return userToken.map(UserToken::getUser);
    }

    public UserToken authenticate(String login, String password) {
        Optional<User> optionalUser = userService.getByLogin(login);
        return getUserTokenIfPasswordIsValid(optionalUser.orElseThrow(SecurityException::new), password);
    }

    private UserToken getUserTokenIfPasswordIsValid(User user, String password) {
        String expectedPassword = DigestUtils.sha1Hex(password + user.getSalt());
        if (user.getPassword().equals(expectedPassword)) {
            user.setPassword(null);
            user.setSalt(null);
            return getTokenForUser(user);
        } else {
            throw new SecurityException("User credentials are invalid");
        }
    }

    private UserToken getTokenForUser(User user) {
        String uuid = UUID.randomUUID().toString();
        logger.info("Uuid {} assigned to user {}", uuid, user.getLogin());
        UserToken userToken = new UserToken(uuid, user, LocalDateTime.now());
        userTokenList.add(userToken);
        return userToken;
    }
}