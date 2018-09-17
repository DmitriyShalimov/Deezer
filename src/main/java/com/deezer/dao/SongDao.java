package com.deezer.dao;

import com.deezer.entity.Song;

import java.util.List;

public interface SongDao {
    Song getSong(int id);
    List<Song> getSongByGenre(int genreId);
}
