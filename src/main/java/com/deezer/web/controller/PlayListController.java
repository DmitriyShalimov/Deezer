package com.deezer.web.controller;

import com.deezer.entity.Song;
import com.deezer.service.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayListController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SongService songService;


    public PlayListController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/album/{id}")
    @ResponseBody
    List<Song> getSongsByAlbum(@PathVariable Integer id) {
        logger.info("Retrieving songs of album {}", id);
        List<Song> songsByAlbum = songService.getSongsByAlbum(id);
        logger.info("Songs of album {} are {}", id, songsByAlbum);
        return songsByAlbum;
    }

    @GetMapping(value = "/genre/{id}")
    @ResponseBody
    List<Song> getSongsByGenre(@PathVariable Integer id) {
        logger.info("Retrieving songs of genre {}", id);
        List<Song> songsByGenre = songService.getSongsByGenre(id);
        logger.info("Songs of genre {} are {}", id, songsByGenre);
        return songsByGenre;
    }


    @GetMapping(value = "/artist/{id}")
    @ResponseBody List<Song> getSongsByArtist(@PathVariable Integer id) {
        logger.info("Retrieving songs of artist {}", id);
        List<Song> songs = songService.getSongsByArtist(id);
        logger.info("Songs of artist {} are {}", id, songs);
        return songs;
    }
}
