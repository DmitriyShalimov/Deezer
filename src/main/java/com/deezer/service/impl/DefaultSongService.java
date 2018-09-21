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
    public Song getSong(int id) {
        return songDao.getSong(id);
    }

    @Override
    public List<Song> getSongsByArtist(int artistId) {
        return songDao.getSongsByArtist(artistId);
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId) {
        return songDao.getSongsByAlbum(albumId);
    }

    @Override
    public List<Song> getSongsByPlayList(int playListId) {
        return songDao.getSongsByPlayList(playListId);
    }

    @Override
    public List<Song> getSongsByGenre(int genreId) {
        return songDao.getSongsByGenre(genreId);
    }
}
