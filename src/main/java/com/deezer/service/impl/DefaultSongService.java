package com.deezer.service.impl;

import com.deezer.dao.SongDao;
import com.deezer.entity.Song;
import com.deezer.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSongService implements SongService {
    private final SongDao songDao;

    @Autowired
    public DefaultSongService(SongDao songDao) {
        this.songDao = songDao;
    }

    @Override
    public Song getSong(int id, int userId) {
        return songDao.getSong(id, userId);
    }

    @Override
    public List<Song> getSongsByArtist(int artistId, int userId) {
        return songDao.getSongsByArtist(artistId, userId);
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId, int userId) {
        return songDao.getSongsByAlbum(albumId, userId);
    }

    @Override
    public List<Song> getSongsByPlayList(int playListId, int userId) {
        return songDao.getSongsByPlayList(playListId, userId);
    }

    @Override
    public List<Song> getSongsByMask(String mask, int userId) {
        return songDao.getSongsByMask(mask, userId);
    }

    @Override
    public List<Song> getRandomSongs(int userId) {
        return songDao.getRandomSongs(userId);
    }

    @Override
    public void likeSong(int songId, int userId) {
        songDao.likeSong(songId, userId);
    }

    @Override
    public String getSongLikeCount(int id) {
        return songDao.getSongLikeCount(id);
    }

    @Override
    public List<Song> getSongsByGenre(int genreId, int userId) {
        return songDao.getSongsByGenre(genreId, userId);
    }
}
