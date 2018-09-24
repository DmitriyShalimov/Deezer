package com.deezer.web.controller;

import com.deezer.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainPageController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final GenreService genreService;
    private final ArtistService artistService;

    @Autowired
    public MainPageController(GenreService genreService, ArtistService artistService) {
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public String getSong(ModelMap model) {
        logger.info("Start loading main page");
        model.addAttribute("genres", genreService.getGenres());
        model.addAttribute("artists", artistService.getAll());
        return "main";
    }
}