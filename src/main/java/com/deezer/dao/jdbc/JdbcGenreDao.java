package com.deezer.dao.jdbc;

import com.deezer.dao.GenreDao;
import com.deezer.dao.jdbc.mapper.GenreRowMapper;
import com.deezer.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcGenreDao implements GenreDao {
    private  final Logger logger = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final GenreRowMapper GENRE_ROW_MAPPER=new GenreRowMapper();
    private static final String GET_ALL_GENRES_SQL = "SELECT id,title FROM genre";
    @Autowired
    public JdbcGenreDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public List<Genre> getGenres() {
        logger.info("start receiving  all genres ");
        return namedParameterJdbcTemplate.query(GET_ALL_GENRES_SQL, GENRE_ROW_MAPPER);
    }
}