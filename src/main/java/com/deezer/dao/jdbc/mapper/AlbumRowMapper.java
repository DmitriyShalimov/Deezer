package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Album;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AlbumRowMapper implements RowMapper<Album> {
    @Override
    public Album mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Album album=new Album();
        album.setId(resultSet.getInt("id"));
        album.setTitle(resultSet.getString("title"));
        return album;
    }
}
