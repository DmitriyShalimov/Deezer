package com.deezer.dao.jdbc;

import com.deezer.dao.ArtistDao;
import com.deezer.dao.jdbc.mapper.ArtistRowMapper;
import com.deezer.entity.Artist;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcArtistDao implements ArtistDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Artist> ARTIST_ROW_MAPPER = new ArtistRowMapper();
    private String getAllArtistSql;
    private String getArtistsByMaskSql;
    private String getArtistByIdSql;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcArtistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Artist> getAll() {
        logger.info("Start query to get all artists from DB");
        long startTime = System.currentTimeMillis();
        List<Artist> artists = jdbcTemplate.query(getAllArtistSql, ARTIST_ROW_MAPPER);
        logger.info("Finish query to get all artists from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return artists;
    }

    @Override
    public List<Artist> getArtistsByMask(String mask) {
        logger.info("Start query to get artists by mask {} from DB", mask);
        long startTime = System.currentTimeMillis();
        List<Artist> artists = jdbcTemplate.query(getArtistsByMaskSql, ARTIST_ROW_MAPPER, "%" + mask + "%");
        logger.info("Finish query to get artists by mask {} from DB. It took {} ms", mask, System.currentTimeMillis() - startTime);
        return artists;
    }

    @Override
    public Artist getById(int id) {
        logger.info("Start query to get artist with id {} from DB", id);
        long startTime = System.currentTimeMillis();
        Artist artist = jdbcTemplate.queryForObject(getArtistByIdSql, ARTIST_ROW_MAPPER, id);
        logger.info("Finish query to get artist with id {} from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return artist;
    }

    @Autowired
    public void setGetAllArtistSql(String getAllArtistSql) {
        this.getAllArtistSql = getAllArtistSql;
    }

    @Autowired
    public void setGetArtistsByMaskSql(String getArtistsByMaskSql) {
        this.getArtistsByMaskSql = getArtistsByMaskSql;
    }

    @Autowired
    public void setGetArtistByIdSql(String getArtistByIdSql) {
        this.getArtistByIdSql = getArtistByIdSql;
    }
}
