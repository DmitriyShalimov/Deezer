package com.deezer.service.impl;

import com.deezer.dao.SongDao;
import com.deezer.entity.Song;
import com.deezer.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
