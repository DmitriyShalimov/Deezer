package com.deezer.web.controller;

import com.deezer.entity.Song;
import com.deezer.entity.User;
import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Song getSong(@PathVariable Integer id) {
        return songService.getSong(id);
    }

    @GetMapping(value = "/song/search/{mask}")
    @ResponseBody
    List<Song> getSongsByMask(@PathVariable String mask) {
        return songService.getSongsByMask(mask);
    }

    @GetMapping(value = "/random")
    @ResponseBody
    List<Song> getRandomSongs() {
        return songService.getRandomSongs();
    }

    @PostMapping(value = "/song/like")
    @ResponseBody
    public String likeSong(@RequestParam String songId, HttpSession session) {
        boolean isLiked =songService.likeSong(Integer.parseInt(songId), ((User) session.getAttribute("loggedUser")).getId());
        if (isLiked) {
            return "success";
        }
        return "error";
    }

    @GetMapping(value = "/song/like/{id}")
    @ResponseBody
    String getSongLikeCount(@PathVariable Integer id) {
        return songService.getSongLikeCount(id);
    }

}
