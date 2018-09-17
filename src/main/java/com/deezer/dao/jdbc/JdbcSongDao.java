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

import java.util.List;

@Repository
public class JdbcSongDao implements SongDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final SongRowMapper SONG_ROW_MAPPER = new SongRowMapper();
    private static final String GET_SONG_BY_LOGIN = "SELECT  id,title ,track_url FROM song  WHERE id=:id";
    private static final String GET_ALL_SONGS_BY_GENRE_SQL = "SELECT  s.id ,s.title , s.track_url FROM song AS s  \n" +
            "LEFT OUTER JOIN song_genre As sg\n" +
            "ON s.id=sg.song\n" +
            "LEFT OUTER JOIN genre AS g\n" +
            "ON sg.genre=g.id\n" +
            "WHERE g.id=:genreId";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcSongDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Song getSong(int id) {
        logger.info("Start receiving song with id {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_SONG_BY_LOGIN, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongByGenre(int genreId) {
        logger.info("start receiving a songs by genre with id {}", genreId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreId", genreId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_GENRE_SQL, params, SONG_ROW_MAPPER);
    }
}
