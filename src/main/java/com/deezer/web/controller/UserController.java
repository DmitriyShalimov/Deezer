package com.deezer.web.controller;

import com.deezer.entity.User;
import com.deezer.security.SecurityService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private SecurityService securityService;

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody String json, HttpSession session) {
        User user = securityService.authenticate(getName(json), getPassword(json));
        if (user != null) {
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
    public ResponseEntity addUser(@RequestBody String json, HttpSession session) {
        User user = new User(getName(json), getPassword(json));
        securityService.add(user);
        session.setAttribute("loggedUser", user);
        return new ResponseEntity(HttpStatus.OK);

    }

    private String getName(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);

            JSONObject jsonObject = (JSONObject) object;
            return String.valueOf(jsonObject.get("login"));
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while get login from json ", e);
        }

    }

    private String getPassword(String json) {
        JSONParser parser = new JSONParser();
        try {
            Object object = parser.parse(json);
            JSONObject jsonObject = (JSONObject) object;
            return String.valueOf(jsonObject.get("password"));
        } catch (ParseException e) {
            throw new RuntimeException("Error occurred while get password from json", e);
        }

    }
}