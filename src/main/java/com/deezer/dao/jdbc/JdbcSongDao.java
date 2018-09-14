package com.deezer.dao.jdbc;

import com.deezer.dao.SongDao;
import com.deezer.dao.jdbc.mapper.SongRowMapper;
import com.deezer.entity.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSongDao implements SongDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final SongRowMapper SONG_ROW_MAPPER = new SongRowMapper();
    private static final String GET_SONG_BY_LOGIN = "SELECT  id,title ,track_url FROM song  WHERE id=:id";
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Song getSong(int id) {
        logger.info("start receiving a song by id");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_SONG_BY_LOGIN, params, SONG_ROW_MAPPER);
    }
}
