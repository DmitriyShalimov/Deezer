package com.deezer.web.controller.view;

import com.deezer.entity.User;
import com.deezer.service.security.SecurityService;
import com.deezer.service.security.entity.UserToken;
import com.deezer.web.controller.view.dto.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SecurityService securityService;

    @Autowired
    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(path = "/login")
    public UserToken doLogin(@RequestParam String login, @RequestParam String password) {
        UserToken userToken = securityService.authenticate(login, password);
        logger.info("User {} logged in", login);
        return userToken;
    }

    @GetMapping(path = "/validate/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse validateToken(@PathVariable String token) {
        Optional<User> userByToken = securityService.getUserByToken(token);
        boolean isValid = userByToken.isPresent();
        logger.info("Token {} is valid: {}", token, isValid);
        return new AuthResponse(isValid);
    }

    @GetMapping(value = "/logout")
    public void logout(@RequestHeader("User-Token") String token) {
        logger.info("Logging out user with token {}", token);
        securityService.logout(token);
    }

    @PostMapping(value = "/registration", produces = MediaType.APPLICATION_JSON_VALUE)
    public UserToken register(@RequestParam String login, @RequestParam String password) {
        User user = new User(login, password);
        UserToken userToken = securityService.register(user);
        logger.info("User {} registered and logged in", login);
        return userToken;
    }
}