package com.deezer.dao.jdbc;

import com.deezer.dao.AlbumDao;
import com.deezer.dao.jdbc.mapper.AlbumRowMapper;
import com.deezer.entity.Album;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcAlbumDao implements AlbumDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final AlbumRowMapper ALBUM_ROW_MAPPER = new AlbumRowMapper();
    private static final String GET_ALL_ALBUMS_BY_ARTIST_ID_SQL = "SELECT  al.id ,al.title, " +
            "al.picture_link, ar.name as artist_name " +
            "FROM album al join artist ar on al.artist = ar.id " +
            "WHERE al.artist=:artistId";
    private static final String GET_ALBUMS_BY_MASK_SQL = "SELECT  al.id ,al.title, " +
            "al.picture_link, ar.name as artist_name " +
            "FROM album al join artist ar on al.artist = ar.id " +
            "WHERE lower(title) like lower(:mask)";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    public JdbcAlbumDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    @Override
    public List<Album> getAlbums(int artistId) {
        logger.info("start receiving albums by artist with id {}", artistId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artistId", artistId);
        return namedParameterJdbcTemplate.query(GET_ALL_ALBUMS_BY_ARTIST_ID_SQL, params, ALBUM_ROW_MAPPER);
    }

    @Override
    public List<Album> getAlbumsByMask(String mask) {
        logger.info("Start receiving albums by mask {}", mask);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mask", "%"+mask+"%");
        return namedParameterJdbcTemplate.query(GET_ALBUMS_BY_MASK_SQL, params, ALBUM_ROW_MAPPER);
    }
}
