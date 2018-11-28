package com.deezer.web.controller;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import com.deezer.entity.Song;
import com.deezer.entity.User;
import com.deezer.service.PlayListService;
import com.deezer.service.SongService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/playlist")
public class PlayListController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final PlayListService playListService;


    public PlayListController(PlayListService playListService) {
        this.playListService = playListService;
    }

    @PostMapping
    public void addPlaylist(@RequestParam String access, @RequestParam String title,
                            @RequestParam int song, HttpSession session) {
        logger.info("Sending request to create playlist {} and song {} to it", title, song);
        int userId = Util.getUserIdFromHttpSession(session);
        long start = System.currentTimeMillis();
        playListService.addPlaylist(title, Access.getTypeById(access), userId, song);
        logger.info("Playlist {} created. It took {} ms", title, System.currentTimeMillis() - start);
    }

    @GetMapping(value = "{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public PlayList getPlaylistById(@PathVariable int id, HttpSession session) {
        logger.info("Sending request to get playlist {} metadata", id);
        long start = System.currentTimeMillis();
        PlayList playList = playListService.getById(id, Util.getUserIdFromHttpSession(session));
        logger.info("Received playlist {}. It took {} ms", playList, System.currentTimeMillis() - start);
        return playList;
    }

    @GetMapping(value = "user", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<PlayList> getUserPlayLists(HttpSession httpSession) {
        int id = Util.getUserIdFromHttpSession(httpSession);
        logger.info("Sending request to get playlists of user {}", id);
        long start = System.currentTimeMillis();
        List<PlayList> playLists = playListService.getUserPlaylist(id);
        logger.info("Playlists of user {} are {}.It took {} ms", id, System.currentTimeMillis() - start);
        return playLists;
    }

    @PostMapping(value = "{playlistId}/song/{songId}")
    public void addSongToPlaylist(@PathVariable int playlistId, @PathVariable int songId) {
        logger.info("Sending request to add song {} to playlist {}", songId, playlistId);
        long start = System.currentTimeMillis();
        playListService.addSongToPlaylist(playlistId, songId);
        logger.info("Song {} added to playlist {}.It took {} ms", songId, playlistId, System.currentTimeMillis() - start);
    }

    @PostMapping(value = "{id}/like")
    public void likePlaylist(@PathVariable int id, HttpSession session) {
        logger.info("Sending request to add like to playlist {}", id);
        long start = System.currentTimeMillis();
        playListService.likePlaylist(id, Util.getUserIdFromHttpSession(session));
        logger.info("Like added to playlist {}.It took {} ms", id, System.currentTimeMillis() - start);
    }

    @GetMapping(value = "/like/{id}", produces =  MediaType.APPLICATION_JSON_VALUE)
    public Integer getSongLikeCount(@PathVariable int id) {
        logger.info("Sending request to get like count of playlist {}", id);
        long start = System.currentTimeMillis();
        Integer likeCount = playListService.getPlaylistLikeCount(id);
        logger.info("Playlist {} has {} likes. It took {} ms", id, likeCount, System.currentTimeMillis() - start);
        return likeCount;
    }

    @GetMapping(value = "top", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<PlayList> getTopPlaylists(HttpSession session) {
        logger.info("Sending request to get top playlists");
        long start = System.currentTimeMillis();
        List<PlayList> playLists = playListService.getTopPlaylists(Util.getUserIdFromHttpSession(session));
        logger.info("Top playlists are {}. It took {} ms", playLists, System.currentTimeMillis() - start);
        return playLists;
    }

    @GetMapping(value = "/liked", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<PlayList> getLikedPlaylists(HttpSession session) {
        int userId = Util.getUserIdFromHttpSession(session);
        logger.info("Sending request to get playlists liked by user {}", userId);
        long start = System.currentTimeMillis();
        List<PlayList> playLists = playListService.getLikedPlaylists(userId);
        logger.info("User {} likes {} playlists. It took {} ms", userId, playLists, System.currentTimeMillis() - start);
        return playLists;
    }

    @GetMapping(value = "/public", produces =  MediaType.APPLICATION_JSON_VALUE)
    public List<PlayList> getAllPublicPlaylists(HttpSession session) {
        logger.info("Sending request to get all public playlists");
        long start = System.currentTimeMillis();
        List<PlayList> playlists = playListService.getAllPublicPlaylists(Util.getUserIdFromHttpSession(session));
        logger.info("Public playlists are {}. It took {} ms", playlists, System.currentTimeMillis() - start);
        return playlists;
    }
}
