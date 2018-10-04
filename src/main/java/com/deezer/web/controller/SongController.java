package com.deezer.web.controller;

import com.deezer.entity.Song;
import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
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
    public Song getSong(@PathVariable int id, HttpSession session){
        int userId = Util.getUserIdFromHttpSession(session);
        return songService.getSong(id, userId);
    }

    @GetMapping(value = "/song/search/{mask}")
    @ResponseBody
    List<Song> getSongsByMask(@PathVariable String mask, HttpSession session) {
        int userId = Util.getUserIdFromHttpSession(session);
        return songService.getSongsByMask(mask, userId);
    }

    @GetMapping(value = "/random")
    @ResponseBody
    List<Song> getRandomSongs(HttpSession session) {
        return songService.getRandomSongs(Util.getUserIdFromHttpSession(session));
    }

    @PostMapping(value = "/song/{id}/like")
    @ResponseBody
    public void likeSong(@PathVariable int id, HttpSession session) {
        songService.likeSong(id, Util.getUserIdFromHttpSession(session));
    }

    @GetMapping(value = "/song/like/{id}")
    @ResponseBody
    String getSongLikeCount(@PathVariable int id) {
        return songService.getSongLikeCount(id);
    }

}
