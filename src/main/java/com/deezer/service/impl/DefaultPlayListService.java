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
    public void addPlaylist(String playlistTitle, Access access, int userId, int songId) {
        playListDao.addPlaylist(playlistTitle, access, userId, songId);
    }

    @Override
    public void addSongToPlaylist(int playlistId, int songId) {
        playListDao.addSongToPlaylist(playlistId, songId);
    }

    @Override
    public List<PlayList> getUserPlaylist(int id) {
        return playListDao.getUserPlaylist(id);
    }

    @Override

    public void likePlaylist(int playlistId, int userId) {
        playListDao.likePlaylist(playlistId, userId);
    }

    @Override
    public Integer getPlaylistLikeCount(int id) {
        return playListDao.getPlaylistLikeCount(id);
    }

    @Override
    public List<PlayList> getTopPlaylists(int userId) {
        return playListDao.getTopPlaylists(userId);
    }

    @Override
    public PlayList getById(int id, int userId) {
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
