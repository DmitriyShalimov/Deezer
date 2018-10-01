package com.deezer.dao;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListDao {
    List<PlayList> getUserPlaylist(Integer id);

    String getPlaylistLikeCount(int id);

    boolean likePlaylist(int playlistId, int userId);

    boolean addPlaylist(String playlistTitle, Access access, int userId, int songId);

    boolean addSongToPlaylist(int playlistId, int songId);

    List<PlayList> getTopPlaylists();

    PlayList getById(Integer id);

}
