package com.deezer.web.controller;

import com.deezer.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainPageController {
    private final GenreService genreService;
    private final ArtistService artistService;

    @Autowired
    public MainPageController(GenreService genreService, ArtistService artistService) {
        this.genreService = genreService;
        this.artistService = artistService;
    }

    @GetMapping()
    public String loadMainPage(ModelMap model) {
        model.addAttribute("genres", genreService.getAll());
        model.addAttribute("artists", artistService.getAll());
        return "index";
    }
}