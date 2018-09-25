package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Album;
import com.deezer.entity.Artist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Album album=new Album();
        album.setId(resultSet.getInt("id"));
        album.setTitle(resultSet.getString("title"));
        album.setPicture(resultSet.getString("picture_link"));
        album.setArtist(new Artist(resultSet.getString("artist_name")));
        return album;
    }
}
