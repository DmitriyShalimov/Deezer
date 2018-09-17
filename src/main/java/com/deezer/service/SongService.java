package com.deezer.service;

import com.deezer.entity.Song;

import java.util.List;

public interface SongService {
    Song getSong(int id);
    List<Song> getSongByGenre(int genreId);
}
