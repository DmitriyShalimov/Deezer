package com.deezer.web.controller;

import com.deezer.entity.User;
import com.deezer.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestParam String login, @RequestParam String password, HttpSession session) {
        Optional<User> optionalUser = securityService.authenticate(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute("loggedUser", user);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        return "redirect:/";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String getSong() {
        return "registration";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ResponseEntity addUser(@RequestParam String login, @RequestParam String password, HttpSession session) {
        User user = new User(login,password);
        securityService.add(user);
        session.setAttribute("loggedUser", user);
        return new ResponseEntity(HttpStatus.OK);
    }
}