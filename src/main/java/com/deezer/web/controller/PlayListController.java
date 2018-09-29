package com.deezer.web.controller;

import com.deezer.entity.*;
import com.deezer.service.*;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PlayListController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SongService songService;
    private final PlayListService playListService;


    public PlayListController(SongService songService, PlayListService playListService) {
        this.songService = songService;
        this.playListService = playListService;
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
    @ResponseBody
    List<Song> getSongsByArtist(@PathVariable Integer id) {
        logger.info("Retrieving songs of artist {}", id);
        List<Song> songs = songService.getSongsByArtist(id);
        logger.info("Songs of artist {} are {}", id, songs);
        return songs;
    }

    @GetMapping(value = "/playlist/{id}")
    @ResponseBody
    List<Song> getSongsByPlaylist(@PathVariable Integer id) {
        logger.info("Retrieving songs from playlist {}", id);
        List<Song> songs = songService.getSongsByPlayList(id);
        logger.info("Songs from playlist {} are {}", id, songs);
        return songs;
    }

    @PostMapping(value = "/playlist/add")
    @ResponseBody
        public String addPlaylist(@RequestParam String playlistTitle, @RequestParam String access,
                                  @RequestParam Integer song, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        boolean isAdded = playListService.addPlaylist(playlistTitle, Access.getTypeById(access), user.getId(), song);
        if (isAdded) {
            return "success";
        }
        return "error";
    }

    @GetMapping(value = "/playlist/user/{id}")
    @ResponseBody
    List<PlayList> getUserPlayLists(@PathVariable Integer id) {
        logger.info("Start retrieving albums of user {}", id);
        List<PlayList> albums = playListService.getUserPlaylist(id);
        logger.info("Albums of user {} are {}", id, albums);
        return albums;
    }

    @PostMapping(value = "/playlist/add/song")
    @ResponseBody
    public String addSongToPlaylist(@RequestParam int playlistId, @RequestParam int songId) {
        boolean isAdded = playListService.addSongToPlaylist(playlistId, songId);
        if (isAdded) {
            return "success";
        }
        return "error";
    }
}
