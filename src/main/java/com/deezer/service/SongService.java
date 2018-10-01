package com.deezer.service;

import com.deezer.entity.Song;

import java.util.List;

public interface SongService {
    Song getSong(int id, int userId);

    List<Song> getSongsByGenre(int genreId, int userId);

    List<Song> getSongsByArtist(int artistId, int userId);

    List<Song> getSongsByAlbum(int albumId, int userId);

    List<Song> getSongsByPlayList(int playListId, int userId);

    List<Song> getSongsByMask(String mask, int userId);

    List<Song> getRandomSongs(int userId);

    void likeSong(int songId, int userId);

    String getSongLikeCount(int id);
}
