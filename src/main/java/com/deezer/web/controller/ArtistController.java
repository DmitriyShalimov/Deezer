package com.deezer.web.controller;

import com.deezer.entity.Artist;
import com.deezer.service.ArtistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArtistController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private ArtistService artistService;

    @Autowired
    public ArtistController(ArtistService artistService) {
        this.artistService = artistService;
    }

    @GetMapping(value = "/artist/search/{mask}")
    @ResponseBody
    List<Artist> getArtistsByMask(@PathVariable String mask) {
        logger.info("Start retrieving artist by mask {}", mask);
        return artistService.getArtistsByMask(mask);
    }

    @GetMapping(value = "/artists")
    @ResponseBody
    List<Artist> getAllArtists() {
        return artistService.getAll();
    }
}
