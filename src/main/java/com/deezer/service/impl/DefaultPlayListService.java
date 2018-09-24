package com.deezer.service.impl;

import com.deezer.dao.PlayListDao;
import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import com.deezer.service.PlayListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultPlayListService implements PlayListService {
    private final PlayListDao playListDao;

    @Autowired
    public DefaultPlayListService(PlayListDao playListDao) {
        this.playListDao = playListDao;
    }

    @Override
    public List<PlayList> getAll() {
        return playListDao.getAll();
    }

    @Override
    public boolean addPlaylist(String playlistTitle, Access access, int userId) {
        return playListDao.addPlaylist(playlistTitle, access, userId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        return playListDao.addSongToPlaylist(playlistId, songId);
    }
}
