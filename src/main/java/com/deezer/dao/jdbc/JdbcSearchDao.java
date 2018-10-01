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
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class JdbcSearchDao implements SearchDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RowMapper<Song> SONG_ROW_MAPPER = new SongRowMapper();
    private final RowMapper<Album> ALBUM_ROW_MAPPER = new AlbumRowMapper();
    private final RowMapper<Artist> ARTIST_ROW_MAPPER = new ArtistRowMapper();
    private static final String GET_SONGS = "select s.id ,s.title, " +
            "s.track_url,s.picture_link, " +
            "al.title as album_title, art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId and su.song = s.id ";
    private static final String GET_ALBUMS = "SELECT  al.id ,al.title, " +
            "al.picture_link, ar.name as artist_name " +
            "FROM album al join artist ar on al.artist = ar.id;";
    private static final String GET_ARTISTS = "SELECT id,name, picture FROM artist";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcSearchDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<SearchResult> getSearchResults(int userId) {
        List<SearchResult> searchResults = new ArrayList<>();
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        logger.info("Getting songs");
        searchResults.addAll(namedParameterJdbcTemplate.query(GET_SONGS, params, SONG_ROW_MAPPER));
        logger.info("Getting albums");
        searchResults.addAll(namedParameterJdbcTemplate.query(GET_ALBUMS, ALBUM_ROW_MAPPER));
        logger.info("Getting artists");
        searchResults.addAll(namedParameterJdbcTemplate.query(GET_ARTISTS, ARTIST_ROW_MAPPER));
        return searchResults;
    }
}
