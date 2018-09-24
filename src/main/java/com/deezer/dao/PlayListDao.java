package com.deezer.dao;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;

import java.util.List;

public interface PlayListDao {
   List<PlayList> getAll();
   boolean addPlaylist(String playlistTitle, Access access, int userId);
   boolean addSongToPlaylist(int playlistId, int songId);
}
