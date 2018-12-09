package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Album;
import com.deezer.entity.Artist;
import com.deezer.entity.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetailedSongRowMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song();
        song.setId(resultSet.getInt("id"));
        song.setTitle(resultSet.getString("title"));
        song.setUrl(resultSet.getString("track_url"));
        Album album = new Album(resultSet.getString("album_title"));
        album.setId(resultSet.getInt("album_id"));
        song.setAlbum(album);
        song.setPicture(resultSet.getString("picture_link"));
        Artist artist = new Artist(resultSet.getString("artist_name"));
        artist.setId(resultSet.getInt("artist_id"));
        song.setArtist(artist);
        song.setLiked(resultSet.getInt("liked") != 0);
        song.setLyrics(resultSet.getString("lyrics"));
        return song;
    }
}
