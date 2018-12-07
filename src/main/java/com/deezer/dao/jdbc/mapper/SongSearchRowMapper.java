package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Album;
import com.deezer.entity.Artist;
import com.deezer.entity.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongSearchRowMapper implements RowMapper<Song> {
    @Override
    public Song mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Song song = new Song();
        song.setId(resultSet.getInt("id"));
        song.setTitle(resultSet.getString("title"));
        song.setAlbum(new Album(resultSet.getString("album_title")));
        song.setPicture(resultSet.getString("picture_link"));
        song.setArtist(new Artist(resultSet.getString("artist_name")));
        return song;
    }
}
