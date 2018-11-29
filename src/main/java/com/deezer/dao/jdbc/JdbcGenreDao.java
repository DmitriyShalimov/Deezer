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
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private String getAllGenresSql;
    private String getGenreByIdSql;
    private String getUserLikedGenresSql;

    @Autowired
    public JdbcGenreDao(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
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

    @Override
    public List<Integer> getUserLikedGenres(int userId) {
        logger.info("start receiving count of genre which liked for user with id {} ", userId);
        long startTime = System.currentTimeMillis();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        return namedParameterJdbcTemplate.query(getUserLikedGenresSql, params, (rs, rowNum) -> rs.getInt(1));
    }

    @Autowired
    public void setGetAllGenresSql(String getAllGenresSql) {
        this.getAllGenresSql = getAllGenresSql;
    }

    @Autowired
    public void setGetGenreByIdSql(String getGenreByIdSql) {
        this.getGenreByIdSql = getGenreByIdSql;
    }

    @Autowired
    public void setGetUserLikedGenresSql(String getUserLikedGenresSql) {
        this.getUserLikedGenresSql = getUserLikedGenresSql;
    }
}
