package com.deezer.web.controller;

import com.deezer.entity.Artist;
import com.deezer.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;

@RestController
@RequestMapping("/artist")
public class ArtistController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping(value = "search/{mask}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Artist> getArtistsByMask(@PathVariable String mask) {
        logger.info("Start request to get artist by mask {}", mask);
        long start = System.currentTimeMillis();
        List<Artist> artistsByMask = artistService.getArtistsByMask(mask);
        logger.info("Artist by mask {} are {}. It took {} ms", mask, artistsByMask, System.currentTimeMillis() - start);
        return artistsByMask;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Artist> getAllArtists() {
        return artistService.getAll();
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Artist getArtistById(@PathVariable int id) {
        logger.info("Start retrieving artist {}", id);
        return artistService.getById(id);
    }
}
