package com.deezer.service;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListService {

    void addPlaylist(String playlistTitle, Access access, int userId, int songId);

    void addSongToPlaylist(int playlistId, int songId);

    List<PlayList> getUserPlaylist(int id);

    void likePlaylist(int playlistId, int userId);

    Integer getPlaylistLikeCount(int id);

    List<PlayList> getTopPlaylists(int userId);

    PlayList getById(int id, int userId);

    List<PlayList> getLikedPlaylists(int userId);

    List<PlayList> getAllPublicPlaylists(int userId);
}
