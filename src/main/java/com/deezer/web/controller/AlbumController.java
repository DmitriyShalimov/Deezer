package com.deezer.web.controller;

import com.deezer.entity.Album;
import com.deezer.service.AlbumService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/album")
public class AlbumController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @GetMapping(value = "artist/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<Album> getAlbumsByArtist(@PathVariable int id) {
        logger.info("Sending request to get albums of artist {}", id);
        long start = System.currentTimeMillis();
        List<Album> albums = albumService.getAlbumsByArtistId(id);
        logger.info("Albums of artist {} are {}. It took {} ms", id, albums, System.currentTimeMillis() - start);
        return albums;
    }

    @GetMapping(value = "search/{mask}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<Album> getAlbumsByMask(@PathVariable String mask) {
        logger.info("Start request to get albums by mask {}", mask);
        long start = System.currentTimeMillis();
        List<Album> albumsByMask = albumService.getAlbumsByMask(mask);
        logger.info("Albums by mask {} are {}. It took {} ms", mask, albumsByMask, System.currentTimeMillis() - start);
        return albumsByMask;
    }

    @GetMapping(value = "{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Album getAlbumById(@PathVariable int id) {
        logger.info("Start request to get album {}", id);
        long start = System.currentTimeMillis();
        Album album = albumService.getById(id);
        logger.info("Album received {}. It took {} ms", album, System.currentTimeMillis() - start);
        return album;
    }
}
