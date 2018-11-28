package com.deezer.dao.jdbc;

import com.deezer.dao.AlbumDao;
import com.deezer.dao.jdbc.mapper.AlbumRowMapper;
import com.deezer.entity.Album;
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
public class JdbcAlbumDao implements AlbumDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Album> ALBUM_ROW_MAPPER = new AlbumRowMapper();
    private String getAllAlbumsByArtistIdSql;
    private String getAlbumsByMaskSql;
    private String getAlbumByIdSql;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcAlbumDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Album> getAlbumsByArtistId(int artistId) {
        logger.info("Start query to get albums by artist with id {} from DB", artistId);
        long startTime = System.currentTimeMillis();
        List<Album> albums = jdbcTemplate.query(getAllAlbumsByArtistIdSql, ALBUM_ROW_MAPPER, artistId);
        logger.info("Finish query to get albums by artist with id {} from DB. It took {} ms", artistId, System.currentTimeMillis() - startTime);
        return albums;
    }

    @Override
    public List<Album> getAlbumsByMask(String mask) {
        logger.info("Start query to get albums by mask {} from DB", mask);
        long startTime = System.currentTimeMillis();
        List<Album> albums = jdbcTemplate.query(getAlbumsByMaskSql, ALBUM_ROW_MAPPER, "%" + mask + "%");
        logger.info("Finish query to get albums by mask {} from DB. It took {} ms", mask, System.currentTimeMillis() - startTime);
        return albums;
    }

    @Override
    public Album getById(int id) {
        logger.info("Start query to get album {} from DB", id);
        long startTime = System.currentTimeMillis();
        Album album = jdbcTemplate.queryForObject(getAlbumByIdSql, ALBUM_ROW_MAPPER, id);
        logger.info("Finish query to get album {} from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return album;
    }

    @Autowired
    public void setGetAllAlbumsByArtistIdSql(String getAllAlbumsByArtistIdSql) {
        this.getAllAlbumsByArtistIdSql = getAllAlbumsByArtistIdSql;
    }

    @Autowired
    public void setGetAlbumsByMaskSql(String getAlbumsByMaskSql) {
        this.getAlbumsByMaskSql = getAlbumsByMaskSql;
    }

    @Autowired
    public void setGetAlbumByIdSql(String getAlbumByIdSql) {
        this.getAlbumByIdSql = getAlbumByIdSql;
    }
}
