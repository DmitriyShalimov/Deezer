package com.deezer.service.impl;

import com.deezer.dao.SongDao;
import com.deezer.entity.Song;
import com.deezer.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultSongService implements SongService {
    @Autowired
    private SongDao songDao;

    @Override
    public Song getSong(int id) {
        return songDao.getSong(id);
    }

    @Override
    public List<Song> getSongByGenre(int genreId) {
        return songDao.getSongByGenre(genreId);
    }
}
