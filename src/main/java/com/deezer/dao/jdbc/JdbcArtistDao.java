package com.deezer.dao.jdbc;

import com.deezer.dao.ArtistDao;
import com.deezer.dao.jdbc.mapper.ArtistRowMapper;
import com.deezer.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcArtistDao implements ArtistDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final ArtistRowMapper ARTIST_ROW_MAPPER = new ArtistRowMapper();
    private static final String GET_ALL_ARTIST_SQL = "SELECT id,name FROM artist";

    @Autowired
    public JdbcArtistDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Artist> getAll() {
        logger.info("start receiving  all artists ");
        return namedParameterJdbcTemplate.query(GET_ALL_ARTIST_SQL, ARTIST_ROW_MAPPER);
    }
}
