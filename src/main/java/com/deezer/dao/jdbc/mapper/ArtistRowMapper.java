package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Artist;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArtistRowMapper implements RowMapper<Artist> {
    @Override
    public Artist mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Artist artist=new Artist();
        artist.setId(resultSet.getInt("id"));
        artist.setName(resultSet.getString("name"));
        return artist;
    }
}
