package com.deezer.web.controller;

import com.deezer.entity.Song;
import com.deezer.service.SongService;
import com.deezer.web.security.AuthPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/song")
public class SongController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SongService songService;

    public SongController(SongService songService) {
        this.songService = songService;
    }

    @GetMapping(value = "/album/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsByAlbum(@PathVariable int id, AuthPrincipal principal) {
        logger.info("Start request to get songs of album {}", id);
        long start = System.currentTimeMillis();
        List<Song> songsByAlbum = songService.getSongsByAlbum(id, principal.getUser().getId());
        logger.info("Songs of album {} are {}. It took {} ms", id, songsByAlbum, System.currentTimeMillis() - start);
        return songsByAlbum;
    }

    @GetMapping(value = "/genre/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsByGenre(@PathVariable int id, AuthPrincipal principal) {
        logger.info("Start request to get songs of genre {}", id);
        long start = System.currentTimeMillis();
        List<Song> songsByGenre = songService.getSongsByGenre(id, principal.getUser().getId());
        logger.info("Songs of genre {} are {}. It took {} ms", id, songsByGenre, System.currentTimeMillis() - start);
        return songsByGenre;
    }

    @GetMapping(value = "/artist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsByArtist(@PathVariable int id, AuthPrincipal principal) {
        logger.info("Start request to get songs of artist {}", id);
        long start = System.currentTimeMillis();
        List<Song> songs = songService.getSongsByArtist(id, principal.getUser().getId());
        logger.info("Songs of artist {} are {}. It took {} ms", id, songs, System.currentTimeMillis() - start);
        return songs;
    }

    @GetMapping(value = "/playlist/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsByPlaylist(@PathVariable int id,AuthPrincipal principal) {
        logger.info("Start request to get songs from playlist {}", id);
        long start = System.currentTimeMillis();
        List<Song> songs = songService.getSongsByPlayList(id, principal.getUser().getId());
        logger.info("Songs from playlist {} are {}. It took {} ms", id, songs, System.currentTimeMillis() - start);
        return songs;
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Song getSong(@PathVariable int id, AuthPrincipal principal) {
        int userId = principal.getUser().getId();
        logger.info("Start request to get song {}", id);
        long start = System.currentTimeMillis();
        Song song = songService.getSong(id, userId);
        logger.info("Song is {}. It took {} ms", song, System.currentTimeMillis() - start);
        return song;
    }

    @GetMapping(value = "search/{mask}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getSongsByMask(@PathVariable String mask, AuthPrincipal principal) {
        int userId = principal.getUser().getId();
        logger.info("Start request to get songs by mask {}", mask);
        long start = System.currentTimeMillis();
        List<Song> songs = songService.getSongsByMask(mask, userId);
        logger.info("Songs by mask {} are {}. It took {} ms", mask, songs, System.currentTimeMillis() - start);
        return songs;
    }

    @GetMapping(value = "/random", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Song> getRandomSongs(AuthPrincipal principal) {
        logger.info("Start request to get random songs ");
        long start = System.currentTimeMillis();
        List<Song> songs = songService.getRandomSongs(principal.getUser().getId());
        logger.info("Random songs are {}. It took {} ms", songs, System.currentTimeMillis() - start);
        return songs;
    }

    @PostMapping(value = "{id}/like")
    public void likeSong(@PathVariable int id, AuthPrincipal principal) {
        logger.info("Start request to add like to song {}", id);
        long start = System.currentTimeMillis();
        songService.likeSong(id, principal.getUser().getId());
        logger.info("Like added to song {}. It took {} ms", id, System.currentTimeMillis() - start);
    }

    @GetMapping(value = "{id}/like", produces = MediaType.APPLICATION_JSON_VALUE)
    public Integer getSongLikeCount(@PathVariable int id) {
        logger.info("Start request to get song {} like count", id);
        long start = System.currentTimeMillis();
        Integer songLikeCount = songService.getSongLikeCount(id);
        logger.info("Song {} like count is {}. It took {} ms", id, songLikeCount, System.currentTimeMillis() - start);
        return songLikeCount;
    }

}
