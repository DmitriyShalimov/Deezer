package com.deezer.dao.jdbc;

import com.deezer.dao.ArtistDao;
import com.deezer.dao.jdbc.mapper.ArtistRowMapper;
import com.deezer.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcArtistDao implements ArtistDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<Artist> ARTIST_ROW_MAPPER = new ArtistRowMapper();
    private static final String GET_ALL_ARTIST_SQL = "SELECT id,name, picture FROM artist";
    private static final String GET_ARTISTS_BY_MASK_SQL =
            "SELECT id,name, picture FROM artist" +
                    " where lower(name) like lower(:mask)";
    private static final String GET_ARTIST_BY_ID_SQL =
            "SELECT id,name, picture FROM artist" +
                    " where id = :id;";

    @Autowired
    public JdbcArtistDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Artist> getAll() {
        logger.info("start receiving  all artists ");
        return namedParameterJdbcTemplate.query(GET_ALL_ARTIST_SQL, ARTIST_ROW_MAPPER);
    }

    @Override
    public List<Artist> getArtistsByMask(String mask) {
        logger.info("start receiving artists by mask {}", mask);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mask", "%" + mask + "%");
        return namedParameterJdbcTemplate.query(GET_ARTISTS_BY_MASK_SQL, params, ARTIST_ROW_MAPPER);
    }

    @Override
    public Artist getById(int id) {
        logger.info("start receiving artist {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_ARTIST_BY_ID_SQL, params, ARTIST_ROW_MAPPER);
    }
}
