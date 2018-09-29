package com.deezer.dao;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListDao {
   List<PlayList> getAll();
   List<PlayList> getUserPlaylist(Integer id);
   boolean addPlaylist(String playlistTitle, Access access, int userId, int songId);
   boolean addSongToPlaylist(int playlistId, int songId);
}
