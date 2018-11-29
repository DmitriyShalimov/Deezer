package com.deezer.web.controller.view;

import com.deezer.entity.User;
import com.deezer.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SecurityService securityService;

    @Autowired
    public UserController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping(path = "/login")
    @ResponseBody
    public ResponseEntity doLogin(@RequestParam String login, @RequestParam String password, HttpSession session) {
        Optional<User> optionalUser = securityService.authenticate(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute("loggedUser", user);
            logger.info("User {} logged in", user.getLogin());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        return "redirect:/login";
    }

    @PostMapping(value = "/registration")
    @ResponseBody
    public ResponseEntity register(@RequestParam String login, @RequestParam String password, HttpSession session) {
        User user = new User(login, password);
        boolean registered = securityService.register(user);
        if (registered) {
            session.setAttribute("loggedUser", user);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}