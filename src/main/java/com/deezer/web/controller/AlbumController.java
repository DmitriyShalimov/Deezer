package com.deezer.web.controller;

import com.deezer.service.AlbumService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @RequestMapping(value = "/album/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    String getSongsByAlbum(@PathVariable Integer id) {
        return albumService.getAlbums(id).toString();
    }
}
