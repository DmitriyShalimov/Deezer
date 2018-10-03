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
    public boolean addPlaylist(String playlistTitle, Access access, int userId, int songId) {
        return playListDao.addPlaylist(playlistTitle, access, userId, songId);
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        return playListDao.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public List<PlayList> getUserPlaylist(Integer id) {
        return playListDao.getUserPlaylist(id);
    }

    @Override

    public boolean likePlaylist(int playlistId, int userId) {
        return playListDao.likePlaylist(playlistId, userId);
    }

    @Override
    public String getPlaylistLikeCount(int id) {
        return playListDao.getPlaylistLikeCount(id);
    }

    @Override
    public List<PlayList> getTopPlaylists(int userId) {
        return playListDao.getTopPlaylists(userId);
    }

    @Override
    public PlayList getById(Integer id, int userId) {
        return playListDao.getById(id, userId);

    }

    @Override
    public List<PlayList> getLikedPlaylists(int userId) {
        return playListDao.getLikedPlayLists(userId);
    }

    @Override
    public List<PlayList> getAllPublicPlaylists(int userId) {
        return playListDao.getAllPublicPlaylists(userId);
    }
}
