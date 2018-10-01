package com.deezer.web.controller;

import com.deezer.entity.*;
import com.deezer.service.*;


import org.omg.PortableInterceptor.INACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class PlayListController {
    private static final String LOGGED_USER_KEY = "loggedUser";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final SongService songService;
    private final PlayListService playListService;
    private static final String RESPONSE_SUCCESS = "success";
    private static final String RESPONSE_ERROR = "error";


    public PlayListController(SongService songService, PlayListService playListService) {
        this.songService = songService;
        this.playListService = playListService;
    }

    @GetMapping(value = "/album/{id}/songs")
    @ResponseBody
    List<Song> getSongsByAlbum(@PathVariable Integer id) {
        logger.info("Retrieving songs of album {}", id);
        List<Song> songsByAlbum = songService.getSongsByAlbum(id);
        logger.info("Songs of album {} are {}", id, songsByAlbum);
        return songsByAlbum;
    }

    @GetMapping(value = "/genre/{id}/songs")
    @ResponseBody
    List<Song> getSongsByGenre(@PathVariable Integer id) {
        logger.info("Retrieving songs of genre {}", id);
        List<Song> songsByGenre = songService.getSongsByGenre(id);
        logger.info("Songs of genre {} are {}", id, songsByGenre);
        return songsByGenre;
    }

    @GetMapping(value = "/artist/{id}/songs")
    @ResponseBody
    List<Song> getSongsByArtist(@PathVariable Integer id) {
        logger.info("Retrieving songs of artist {}", id);
        List<Song> songs = songService.getSongsByArtist(id);
        logger.info("Songs of artist {} are {}", id, songs);
        return songs;
    }

    @GetMapping(value = "/playlist/{id}/songs")
    @ResponseBody
    List<Song> getSongsByPlaylist(@PathVariable Integer id) {
        logger.info("Retrieving songs from playlist {}", id);
        List<Song> songs = songService.getSongsByPlayList(id);
        logger.info("Songs from playlist {} are {}", id, songs);
        return songs;
    }

    @PostMapping(value = "/playlist")
    @ResponseBody
    public String addPlaylist(@RequestParam String access, @RequestParam String title,
                              @RequestParam Integer song, HttpSession session) {
        logger.info("Saving playlist {} and adding song {} to it", title, song);
        User user = (User) session.getAttribute(LOGGED_USER_KEY);
        boolean isAdded = playListService.addPlaylist(title, Access.getTypeById(access), user.getId(), song);
        if (isAdded) {
            return RESPONSE_SUCCESS;
        }
        return RESPONSE_ERROR;
    }

    @GetMapping(value = "/playlist/{id}")
    @ResponseBody
    public PlayList getPlaylistById(@PathVariable Integer id) {
        logger.info("Getting playlist {} metadata", id);
        return playListService.getById(id);
    }

    @GetMapping(value = "/playlist/user")
    @ResponseBody
    List<PlayList> getUserPlayLists(HttpSession httpSession) {
        User user = (User) httpSession.getAttribute(LOGGED_USER_KEY);
        int id = user.getId();
        logger.info("Start retrieving playlists of user {}", id);
        List<PlayList> playLists = playListService.getUserPlaylist(id);
        logger.info("Playlists of user {} are {}", id, playLists);
        return playLists;
    }

    @PostMapping(value = "/playlist/{playlistId}/song/{songId}")
    @ResponseBody
    public String addSongToPlaylist(@PathVariable Integer playlistId, @PathVariable Integer songId) {
        boolean isAdded = playListService.addSongToPlaylist(playlistId, songId);
        if (isAdded) {
            return RESPONSE_SUCCESS;
        }
        return RESPONSE_ERROR;
    }

    @PostMapping(value = "/playlist/like")
    @ResponseBody
    public String likeSong(@RequestParam String playlistId, HttpSession session) {
        boolean isLiked = playListService.likePlaylist(Integer.parseInt(playlistId), ((User) session.getAttribute("loggedUser")).getId());
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

    @GetMapping(value = "/top-playlists")
    @ResponseBody
    public List<PlayList> getTopPlaylists() {
        logger.info("Start retrieving top playlists");
        List<PlayList> playLists = playListService.getTopPlaylists();
        logger.info("Top playlists are {}", playLists);
        return playLists;
    }
}
