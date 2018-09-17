package com.deezer.web.controller;

import com.deezer.entity.User;
import com.deezer.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String showLogin() {
        return "login.html";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    @ResponseBody
    public String doLogin(@RequestParam String login, @RequestParam String password, HttpSession session) {
        Optional<User> optionalUser = securityService.authenticate(login, password);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute("loggedUser", user);
            logger.info("User {} logged in", user.getLogin());
            return "success";
        }
        return "error";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session) {
        session.removeAttribute("loggedUser");
        return "redirect:/login";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String registration() {
        return "registration.html";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ResponseBody
    public String register(@RequestParam String login, @RequestParam String password, HttpSession session) {
        User user = new User(login, password);
        boolean registered = securityService.register(user);
        if(registered) {
            session.setAttribute("loggedUser", user);
            return "success";
        }
        return "error";
    }
}