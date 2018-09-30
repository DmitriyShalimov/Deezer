package com.deezer.dao;

import com.deezer.entity.Song;

import java.util.List;

public interface SongDao {
    Song getSong(int id);

    List<Song> getSongsByGenre(int genreId);

    List<Song> getSongsByArtist(int artistId);

    List<Song> getSongsByAlbum(int albumId);

    List<Song> getSongsByPlayList(int playListId);

    List<Song> getSongsByMask(String mask);

    List<Song> getRandomSongs();

    boolean likeSong(int songId, int userId);

    String getSongLikeCount(int id);
}
