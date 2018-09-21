package com.deezer.dao.jdbc;

import com.deezer.dao.SongDao;
import com.deezer.dao.jdbc.mapper.SongRowMapper;
import com.deezer.entity.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcSongDao implements SongDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final SongRowMapper SONG_ROW_MAPPER = new SongRowMapper();
    private static final String GET_SONG_BY_ID = "SELECT  id,title ,track_url FROM song  WHERE id=:id";

    private static final String GET_ALL_SONGS_BY_GENRE_SQL = "SELECT  s.id ,s.title , s.track_url FROM song AS s " +
            "LEFT OUTER JOIN song_genre As sg " +
            "ON s.id=sg.song " +
            "LEFT OUTER JOIN genre AS g " +
            "ON sg.genre=g.id " +
            "WHERE g.id=:genreId";
    private static final String GET_ALL_SONGS_BY_ARTIST_SQL = "SELECT  s.id ,s.title , s.track_url FROM song AS s " +
            "LEFT OUTER JOIN album As al " +
            "ON s.album=al.id " +
            "LEFT OUTER JOIN artist As ar " +
            "ON ar.id=al.artist " +
            "WHERE ar.id=:artistId";
    private static final String GET_ALL_SONGS_BY_ALBUM_SQL="SELECT id ,title , track_url FROM song WHERE album=:albumId";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcSongDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public Song getSong(int id) {
        logger.info("Start receiving song with id {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        return namedParameterJdbcTemplate.queryForObject(GET_SONG_BY_ID, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByGenre(int genreId) {
        logger.info("start receiving songs by genre with id {}", genreId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreId", genreId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_GENRE_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByArtist(int artistId) {
        logger.info("start receiving songs by artist with id {}", artistId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artistId", artistId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_ARTIST_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId) {
        logger.info("start receiving songs by genre with id {}", albumId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("albumId", albumId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_ALBUM_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByPlayList(int playListId) {
        logger.info("start receiving songs by playList with id {}", playListId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("playListId", playListId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_GENRE_SQL, params, SONG_ROW_MAPPER);
    }
}
