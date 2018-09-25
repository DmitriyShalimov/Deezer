package com.deezer.web.controller;

import com.deezer.entity.Song;
import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SongController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/song/{id}")
    @ResponseBody
    public Song getSong(@PathVariable Integer id) {
        return songService.getSong(id);
    }

    @GetMapping(value = "/song/search/{mask}")
    @ResponseBody
    List<Song> getSongsByMask(@PathVariable String mask){
        return songService.getSongsByMask(mask);
    }

}
