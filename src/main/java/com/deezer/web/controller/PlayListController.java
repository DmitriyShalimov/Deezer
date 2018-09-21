package com.deezer.web.controller;

import com.deezer.service.*;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayListController {
    private final SongService songService;


    public PlayListController(SongService songService) {
        this.songService = songService;
    }

    @RequestMapping(value = "/albumsongs/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    String getSongsByAlbum(@PathVariable Integer id) {
        return songService.getSongsByAlbum(id).toString();
    }

    @RequestMapping(value = "/genre/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    String getSongsByGenre(@PathVariable Integer id) {
        return songService.getSongsByGenre(id).toString();
    }

    @RequestMapping(value = "/artist/{id}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    String getSongsByArtist(@PathVariable Integer id) {
        return songService.getSongsByArtist(id).toString();
    }
}
