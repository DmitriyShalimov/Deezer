package com.deezer.web.controller;

import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class SongController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private SongService songService;

    @RequestMapping(method = RequestMethod.GET)
    public String getSong(ModelMap model) {
        model.addAttribute("audio", songService.getSong(1).getUrl());
        model.addAttribute("test_env_var", System.getenv("DB_URL"));
        model.addAttribute("test_env_get_var", System.getenv().get("DB_URL"));
        model.addAttribute("test_prop", System.getProperty("DB_URL"));
        return "hello";
    }
}