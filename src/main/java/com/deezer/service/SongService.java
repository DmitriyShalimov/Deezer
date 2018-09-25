package com.deezer.service;

import com.deezer.entity.Song;

import java.util.List;

public interface SongService {
    Song getSong(int id);
    List<Song> getSongsByGenre(int genreId);
    List<Song> getSongsByArtist(int artistId);
    List<Song> getSongsByAlbum(int albumId);
    List<Song> getSongsByPlayList(int playListId);
    List<Song> getSongsByMask(String mask);
}
