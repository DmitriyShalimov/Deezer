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
    private static final String GET_ALL_ALBUMS_BY_ARTIST_ID_SQL = "SELECT  id ,title  FROM album WHERE artist=:artistId";

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
}