package com.deezer.dao;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListDao {
    List<PlayList> getUserPlaylist(int id);

    Integer getPlaylistLikeCount(int id);

    void likePlaylist(int playlistId, int userId);

    void addPlaylist(String playlistTitle, Access access, int userId, int songId);

    void addSongToPlaylist(int playlistId, int songId);

    List<PlayList> getTopPlaylists(int userId);

    PlayList getById(int id, int userId);

    List<PlayList> getLikedPlayLists(int userId);

    List<PlayList> getAllPublicPlaylists(int userId);
}
