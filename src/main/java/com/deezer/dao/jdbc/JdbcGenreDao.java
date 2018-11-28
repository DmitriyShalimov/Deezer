package com.deezer.dao.jdbc;

import com.deezer.dao.GenreDao;
import com.deezer.dao.jdbc.mapper.GenreRowMapper;
import com.deezer.entity.Genre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcGenreDao implements GenreDao {
    private static final RowMapper<Genre> GENRE_ROW_MAPPER = new GenreRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;
    private String getAllGenresSql;
    private String getGenreByIdSql;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Genre> getGenres() {
        logger.info("Start query to get all genres from DB");
        long startTime = System.currentTimeMillis();
        List<Genre> genres = jdbcTemplate.query(getAllGenresSql, GENRE_ROW_MAPPER);
        logger.info("Finish query to get all genres from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return genres;
    }

    @Override
    public Genre getById(int id) {
        logger.info("Start query to get genre by id {} from DB", id);
        long startTime = System.currentTimeMillis();
        Genre genre = jdbcTemplate.queryForObject(getGenreByIdSql, GENRE_ROW_MAPPER, id);
        logger.info("Finish query to get genre by id {} from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return genre;
    }

    @Autowired
    public void setGetAllGenresSql(String getAllGenresSql) {
        this.getAllGenresSql = getAllGenresSql;
    }

    @Autowired
    public void setGetGenreByIdSql(String getGenreByIdSql) {
        this.getGenreByIdSql = getGenreByIdSql;
    }
}
