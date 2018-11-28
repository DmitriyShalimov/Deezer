package com.deezer.web.controller;

import com.deezer.entity.Genre;
import com.deezer.service.GenreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenreController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping(produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<Genre> getAllGenres() {
        logger.info("Start request to get all genres");
        long start = System.currentTimeMillis();
        List<Genre> genres = genreService.getAll();
        logger.info("Received genres are {}. It took {} ms", genres, System.currentTimeMillis() - start);
        return genres;
    }

    @GetMapping(value = "{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Genre getGenreById(@PathVariable Integer id) {
        logger.info("Start request to get genre by id {}", id);
        long start = System.currentTimeMillis();
        Genre genre = genreService.getGenreById(id);
        logger.info("Received genre is {}. It took {} ms", genre, System.currentTimeMillis() - start);
        return genre;
    }

}
