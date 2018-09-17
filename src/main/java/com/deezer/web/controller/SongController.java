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
    private final SongService songService;

    @Autowired
    public SongController(SongService songService) {
        this.songService = songService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSong(ModelMap model) {
        model.addAttribute("songs", songService.getSongByGenre(1));
        return "main";
    }
}