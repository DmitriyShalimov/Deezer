package com.deezer.service;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListService {

    boolean addPlaylist(String playlistTitle, Access access, int userId, int songId);

    boolean addSongToPlaylist(int playlistId, int songId);

    List<PlayList> getUserPlaylist(Integer id);

    boolean likePlaylist(int playlistId, int userId);

    String getPlaylistLikeCount(int id);

    List<PlayList> getTopPlaylists();

    PlayList getById(Integer id);

}
