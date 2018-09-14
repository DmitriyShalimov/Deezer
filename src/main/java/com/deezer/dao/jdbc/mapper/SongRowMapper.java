package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SongRowMapper implements RowMapper<Song> {
    public Song mapRow(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, 0);
    }

    @Override
    public Song mapRow(ResultSet resultSet, int i) throws SQLException {
       Song song=new Song();
        song.setId(resultSet.getInt("id"));
        song.setTitle(resultSet.getString("title"));
        song.setUrl(resultSet.getString("track_url"));
        return song;
    }
}
