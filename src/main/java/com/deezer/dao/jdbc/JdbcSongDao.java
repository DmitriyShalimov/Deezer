package com.deezer.dao.jdbc;

import com.deezer.dao.SongDao;
import com.deezer.dao.jdbc.mapper.SongRowMapper;
import com.deezer.entity.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcSongDao implements SongDao {
    private static final String USER_ID_KEY = "userId";
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final RowMapper<Song> SONG_ROW_MAPPER = new SongRowMapper();
    private static final String GET_SONG_BY_ID = "select s.id ,s.title, " +
            "s.track_url,s.picture_link, " +
            "al.title as album_title, art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "WHERE s.id = :id";

    private static final String GET_ALL_SONGS_BY_GENRE_SQL = "select s.id ,s.title , " +
            "s.track_url,s.picture_link, " +
            "al.title as album_title, art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "join song_genre sg on sg.song = s.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "WHERE sg.genre=:genreId";
    private static final String GET_ALL_SONGS_BY_ARTIST_SQL = "select s.id " +
            ",s.title, s.track_url" +
            ",s.picture_link, al.title as album_title" +
            ",art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId and su.song = s.id " +
            "WHERE art.id=:artistId";
    private static final String GET_ALL_SONGS_BY_ALBUM_SQL = "select s.id " +
            ",s.title, s.track_url" +
            ",s.picture_link, al.title as album_title" +
            ",art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "WHERE al.id=:albumId";
    private static final String GET_ALL_SONGS_BY_MASK_SQL = "select s.id " +
            ",s.title, s.track_url," +
            "s.picture_link, al.title as album_title, " +
            "art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "WHERE lower(s.title) like lower(:mask);";

    private static final String GET_RANDOM_SONGS = "select s.id," +
            "s.title, s.track_url," +
            "s.picture_link, al.title as album_title," +
            "art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "order by random() limit 42";

    private static final String GET_ALL_SONGS_BY_PLAYLIST_SQL = "select s.id ," +
            "s.title, s.track_url," +
            "s.picture_link, al.title as album_title," +
            "art.name as artist_name " +
            ", su.id as liked " +
            "from song s join album al on s.album = al.id " +
            "join artist art on al.artist = art.id " +
            "join playlist_song pls on pls.song = s.id " +
            "left join song_user su on su.user = :userId  and su.song = s.id " +
            "where pls.playlist =:playlistId";
    private static final String GET_SONG_LIKE_COUNT_SQL = "SELECT COUNT(*) FROM song_user WHERE song =:songId;";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final SimpleJdbcCall likeSong;

    @Autowired
    public JdbcSongDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate, JdbcTemplate jdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.likeSong = new SimpleJdbcCall(jdbcTemplate).withFunctionName("like_song");
    }

    @Override
    public Song getSong(int id, int userId) {
        logger.info("Start receiving song with id {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.queryForObject(GET_SONG_BY_ID, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByGenre(int genreId, int userId) {
        logger.info("start receiving songs by genre with id {}", genreId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("genreId", genreId);
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_GENRE_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByArtist(int artistId, int userId) {
        logger.info("start receiving songs by artist with id {}", artistId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("artistId", artistId);
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_ARTIST_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId, int userId) {
        logger.info("start receiving songs by genre with id {}", albumId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("albumId", albumId);
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_ALBUM_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByPlayList(int playListId, int userId) {
        logger.info("start receiving songs by playList with id {}", playListId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("playlistId", playListId);
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_PLAYLIST_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getSongsByMask(String mask, int userId) {
        logger.info("start receiving songs by mask {}", mask);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("mask", "%" + mask + "%");
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_SONGS_BY_MASK_SQL, params, SONG_ROW_MAPPER);
    }

    @Override
    public List<Song> getRandomSongs(int userId) {
        logger.info("start receiving random songs");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID_KEY, userId);
        return namedParameterJdbcTemplate.query(GET_RANDOM_SONGS, params,SONG_ROW_MAPPER);
    }

    @Override
    @Transactional
    public void likeSong(int songId, int userId) {
        logger.info("User {} triggered like for song {}", userId, songId);
        likeSong.executeFunction(Void.class, userId, songId);
    }

    @Override
    public String getSongLikeCount(int id) {
        logger.info("Get like count for song with id {} ", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", id);
        return namedParameterJdbcTemplate.queryForObject(GET_SONG_LIKE_COUNT_SQL, params, String.class);
    }
}
