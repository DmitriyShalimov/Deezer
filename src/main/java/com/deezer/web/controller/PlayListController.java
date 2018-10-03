package com.deezer.web.controller;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import com.deezer.entity.Song;
import com.deezer.entity.User;
import com.deezer.service.PlayListService;
import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    List<Song> getSongsByAlbum(@PathVariable Integer id, HttpSession session) {
        logger.info("Retrieving songs of album {}", id);
        List<Song> songsByAlbum = songService.getSongsByAlbum(id, Util.getUserIdFromHttpSession(session));
        logger.info("Songs of album {} are {}", id, songsByAlbum);
        return songsByAlbum;
    }

    @GetMapping(value = "/genre/{id}/songs")
    @ResponseBody
    List<Song> getSongsByGenre(@PathVariable Integer id, HttpSession session) {
        logger.info("Retrieving songs of genre {}", id);
        List<Song> songsByGenre = songService.getSongsByGenre(id, Util.getUserIdFromHttpSession(session));
        logger.info("Songs of genre {} are {}", id, songsByGenre);
        return songsByGenre;
    }

    @GetMapping(value = "/artist/{id}/songs")
    @ResponseBody
    List<Song> getSongsByArtist(@PathVariable Integer id, HttpSession session) {
        logger.info("Retrieving songs of artist {}", id);
        List<Song> songs = songService.getSongsByArtist(id, Util.getUserIdFromHttpSession(session));
        logger.info("Songs of artist {} are {}", id, songs);
        return songs;
    }

    @GetMapping(value = "/playlist/{id}/songs")
    @ResponseBody
    List<Song> getSongsByPlaylist(@PathVariable Integer id, HttpSession session) {
        logger.info("Retrieving songs from playlist {}", id);
        List<Song> songs = songService.getSongsByPlayList(id, Util.getUserIdFromHttpSession(session));
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
    public PlayList getPlaylistById(@PathVariable Integer id, HttpSession session) {
        logger.info("Getting playlist {} metadata", id);
        return playListService.getById(id, Util.getUserIdFromHttpSession(session));
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

    @PostMapping(value = "/playlist/{id}/like")
    @ResponseBody
    public String likePlaylist(@PathVariable int id, HttpSession session) {
        boolean isLiked = playListService.likePlaylist(id, Util.getUserIdFromHttpSession(session));
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
    public List<PlayList> getTopPlaylists(HttpSession session) {
        logger.info("Start retrieving top playlists");
        List<PlayList> playLists = playListService.getTopPlaylists(Util.getUserIdFromHttpSession(session));
        logger.info("Top playlists are {}", playLists);
        return playLists;
    }

    @GetMapping(value = "/playlists/liked")
    @ResponseBody
    public List<PlayList> getLikedPlaylists(HttpSession session) {
        int userId = Util.getUserIdFromHttpSession(session);
        logger.info("Start retrieving playlists liked by user {}", userId);
        List<PlayList> playLists = playListService.getLikedPlaylists(userId);
        logger.info("User {} likes {} playlists",userId, playLists);
        return playLists;
    }

    @GetMapping(value = "/playlists")
    @ResponseBody
    public List<PlayList> getAllPublicPlaylists(HttpSession session) {
        logger.info("Start retrieving all public playlists");
        return playListService.getAllPublicPlaylists(Util.getUserIdFromHttpSession(session));
    }
}
