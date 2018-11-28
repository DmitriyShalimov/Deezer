package com.deezer.dao.jdbc;

import com.deezer.dao.SearchDao;
import com.deezer.dao.jdbc.mapper.AlbumRowMapper;
import com.deezer.dao.jdbc.mapper.ArtistRowMapper;
import com.deezer.dao.jdbc.mapper.SongRowMapper;
import com.deezer.entity.Album;
import com.deezer.entity.Artist;
import com.deezer.entity.SearchResult;
import com.deezer.entity.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcSearchDao implements SearchDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RowMapper<Song> SONG_ROW_MAPPER = new SongRowMapper();
    private final RowMapper<Album> ALBUM_ROW_MAPPER = new AlbumRowMapper();
    private final RowMapper<Artist> ARTIST_ROW_MAPPER = new ArtistRowMapper();
    private final JdbcTemplate jdbcTemplate;
    private String getSongsForSearchSql;
    private String getAlbumsForSearchSql;
    private String getArtistsForSearchSql;

    @Autowired
    public JdbcSearchDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<SearchResult> getSearchResults(int userId) {
        List<SearchResult> searchResults = new ArrayList<>();
        logger.info("Start query to get search results for user {} from DB", userId);
        long startTime = System.currentTimeMillis();
        logger.debug("Getting songs");
        List<Song> songs = jdbcTemplate.query(getSongsForSearchSql, SONG_ROW_MAPPER, userId);
        logger.debug("Getting albums");
        List<Album> albums = jdbcTemplate.query(getAlbumsForSearchSql, ALBUM_ROW_MAPPER);
        logger.debug("Getting artists");
        List<Artist> artists = jdbcTemplate.query(getArtistsForSearchSql, ARTIST_ROW_MAPPER);
        logger.info("Finish query to get search results for user {} from DB. It took {} ms", userId, System.currentTimeMillis() - startTime);
        searchResults.addAll(songs);
        searchResults.addAll(albums);
        searchResults.addAll(artists);
        return searchResults;
    }

    @Autowired
    public void setGetSongsForSearchSql(String getSongsForSearchSql) {
        this.getSongsForSearchSql = getSongsForSearchSql;
    }

    @Autowired
    public void setGetAlbumsForSearchSql(String getAlbumsForSearchSql) {
        this.getAlbumsForSearchSql = getAlbumsForSearchSql;
    }

    @Autowired
    public void setGetArtistsForSearchSql(String getArtistsForSearchSql) {
        this.getArtistsForSearchSql = getArtistsForSearchSql;
    }
}
