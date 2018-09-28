package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayListRowMapper implements RowMapper<PlayList> {
    @Override
    public PlayList mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        PlayList playList=new PlayList();
        playList.setId(resultSet.getInt("id"));
        playList.setTitle(resultSet.getString("title"));
        playList.setLikeCount(resultSet.getInt("like_count"));
        playList.setAccess(Access.getTypeById(resultSet.getString("access")));
        return playList;
    }
}
