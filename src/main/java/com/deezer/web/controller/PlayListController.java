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
    private final static String  RESPONSE_SUCCESS="success";
    private final static String   RESPONSE_ERROR="error";


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
    public String addPlaylist(@RequestParam String playlistTitle, @RequestParam String access, HttpSession session) {
        User user = (User) session.getAttribute("loggedUser");
        String accessID = "1".equals(access) ? "private" : "public";
        boolean isAdded = playListService.addPlaylist(playlistTitle, Access.getTypeById(accessID), user.getId());
        if (isAdded) {
            return RESPONSE_SUCCESS;
        }
        return RESPONSE_ERROR;
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
            return RESPONSE_SUCCESS;
        }
        return RESPONSE_ERROR;
    }

    @PostMapping(value = "/playlist/like")
    @ResponseBody
    public String likeSong(@RequestParam String playlistId, HttpSession session) {
        boolean isLiked =playListService.likePlaylist(Integer.parseInt(playlistId), ((User) session.getAttribute("loggedUser")).getId());
        if (isLiked) {
            return RESPONSE_SUCCESS;
        }
        return RESPONSE_ERROR;
    }

    @GetMapping(value = "/playlist/like/{id}")
    @ResponseBody
    String getSongLikeCount(@PathVariable Integer id) {
        return playListService.getPlaylistLikeCount(id);
    }
}
