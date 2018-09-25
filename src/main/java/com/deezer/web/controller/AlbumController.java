package com.deezer.web.controller;

import com.deezer.entity.Album;
import com.deezer.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AlbumController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping(value = "/album/artist/{id}")
    @ResponseBody
    List<Album> getAlbumsByArtist(@PathVariable Integer id) {
        logger.info("Start retrieving albums of artist {}", id);
        List<Album> albums = albumService.getAlbumsByArtistId(id);
        logger.info("Albums of artist {} are {}", id, albums);
        return albums;
    }

    @GetMapping(value = "/album/search/{mask}")
    @ResponseBody
    List<Album> getAlbumsByMask(@PathVariable String mask) {
        logger.info("Start retrieving albums by mask {}", mask);
        return albumService.getAlbumsByMask(mask);
    }
}
