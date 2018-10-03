package com.deezer.web.controller;

import com.deezer.entity.Genre;
import com.deezer.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class GenreController {
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(value = "genres")
    @ResponseBody
    List<Genre> getAllGenres() {
        return genreService.getAll();
    }

    @GetMapping(value = "genre/{id}")
    @ResponseBody
    Genre getGenreById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }

}
