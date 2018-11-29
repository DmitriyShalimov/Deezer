package com.deezer.dao.jdbc;

import com.deezer.dao.SongDao;
import com.deezer.dao.jdbc.mapper.SongDetailsRowMapper;
import com.deezer.entity.Song;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcSongDao implements SongDao {
    private static final String LIKE_SONG_FUNCTION_NAME = "like_song";
    private static final RowMapper<Song> SONG_ROW_MAPPER = new SongDetailsRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcCall likeSong;

    private String getSongByIdSql;
    private String getAllSongsByGenreSql;
    private String getAllSongsByArtistSql;
    private String getAllSongsByAlbumSql;
    private String getAllSongsByMaskSql;
    private String getRandomSongs;
    private String getAllSongsByPlaylistSql;
    private String getSongLikeCountSql;
    private String getAllSongsByGenresSql;


    @Autowired
    public JdbcSongDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.likeSong = new SimpleJdbcCall(jdbcTemplate).withFunctionName(LIKE_SONG_FUNCTION_NAME);
    }

    @Override
    public Song getSong(int id, int userId) {
        logger.info("Start query to get song with id {} from DB", id);
        long startTime = System.currentTimeMillis();
        Song song = jdbcTemplate.queryForObject(getSongByIdSql, SONG_ROW_MAPPER, userId, id);
        logger.info("Finish query to get song with id {} from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return song;
    }

    @Override
    public List<Song> getSongsByGenre(int genreId, int userId) {
        logger.info("Start query to get all songs of genre {} from DB", genreId);
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getAllSongsByGenreSql, SONG_ROW_MAPPER, userId, genreId);
        logger.info("Finish query to get all songs of genre {} from DB. It took {} ms", genreId, System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    public List<Song> getSongsByArtist(int artistId, int userId) {
        logger.info("Start query to get all songs of artist {} from DB", artistId);
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getAllSongsByArtistSql, SONG_ROW_MAPPER, userId, artistId);
        logger.info("Finish query to get all songs of artist {} from DB. It took {} ms", artistId, System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    public List<Song> getSongsByAlbum(int albumId, int userId) {
        logger.info("Start query to get all songs of album {} from DB", albumId);
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getAllSongsByAlbumSql, SONG_ROW_MAPPER, userId, albumId);
        logger.info("Finish query to get all songs of album {} from DB. It took {} ms", albumId, System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    public List<Song> getSongsByPlayList(int playListId, int userId) {
        logger.info("Start query to get all songs of playlist {} from DB", playListId);
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getAllSongsByPlaylistSql, SONG_ROW_MAPPER, userId, playListId);
        logger.info("Finish query to get all songs of playlist {} from DB. It took {} ms", playListId, System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    public List<Song> getSongsByMask(String mask, int userId) {
        logger.info("Start query to get all songs by mask {} from DB", mask);
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getAllSongsByMaskSql, SONG_ROW_MAPPER, userId, "%" + mask + "%");
        logger.info("Finish query to get all songs by mask {} from DB. It took {} ms", mask, System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    public List<Song> getRandomSongs(int userId) {
        logger.info("Start query to get random songs from DB");
        long startTime = System.currentTimeMillis();
        List<Song> songs = jdbcTemplate.query(getRandomSongs, SONG_ROW_MAPPER, userId);
        logger.info("Finish query to get random songs from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return songs;
    }

    @Override
    @Transactional
    public void likeSong(int songId, int userId) {
        logger.info("Start query to add/remove like to song {} by user {} to DB", songId, userId);
        long startTime = System.currentTimeMillis();
        likeSong.executeFunction(Void.class, userId, songId);
        logger.info("Finish query to add/remove like to song {} by user {} to DB. It took {} ms", songId, userId, System.currentTimeMillis() - startTime);
    }

    @Override
    public Integer getSongLikeCount(int id) {
        logger.info("Start query to get song {} like count from DB", id);
        long startTime = System.currentTimeMillis();
        Integer songLikeCount = jdbcTemplate.queryForObject(getSongLikeCountSql, Integer.class, id);
        logger.info("Finish query to get song {} like count from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return songLikeCount;
    }

    @Override
    public List<Song> getRecommendedSongForGenre(int userId, List<Integer> genres) {
        logger.info("start receiving recommended songs for user  with id {}", userId);
        long startTime = System.currentTimeMillis();
        if (genres.size() == 1) {
            List<Song> songs = jdbcTemplate.query(getAllSongsByGenreSql, SONG_ROW_MAPPER, userId, genres.get(0));
            logger.info("Finish query to get all songs of genre {} from DB. It took {} ms", genres.get(0), System.currentTimeMillis() - startTime);
            return songs;
        } else {
            List<Song> songs = jdbcTemplate.query(getAllSongsByGenresSql, SONG_ROW_MAPPER, userId, genres.get(0), genres.get(1));
            logger.info("Finish query to get all songs of genres {} ,{}  from DB. It took {} ms", genres.get(0), genres.get(1),
                    System.currentTimeMillis() - startTime);
            return songs;
        }
    }

    @Autowired
    public void setGetSongByIdSql(String getSongByIdSql) {
        this.getSongByIdSql = getSongByIdSql;
    }

    @Autowired
    public void setGetAllSongsByGenreSql(String getAllSongsByGenreSql) {
        this.getAllSongsByGenreSql = getAllSongsByGenreSql;
    }

    @Autowired
    public void setGetAllSongsByArtistSql(String getAllSongsByArtistSql) {
        this.getAllSongsByArtistSql = getAllSongsByArtistSql;
    }

    @Autowired
    public void setGetAllSongsByAlbumSql(String getAllSongsByAlbumSql) {
        this.getAllSongsByAlbumSql = getAllSongsByAlbumSql;
    }

    @Autowired
    public void setGetAllSongsByMaskSql(String getAllSongsByMaskSql) {
        this.getAllSongsByMaskSql = getAllSongsByMaskSql;
    }

    @Autowired
    public void setGetRandomSongs(String getRandomSongs) {
        this.getRandomSongs = getRandomSongs;
    }

    @Autowired
    public void setGetAllSongsByPlaylistSql(String getAllSongsByPlaylistSql) {
        this.getAllSongsByPlaylistSql = getAllSongsByPlaylistSql;
    }

    @Autowired
    public void setGetSongLikeCountSql(String getSongLikeCountSql) {
        this.getSongLikeCountSql = getSongLikeCountSql;
    }

    @Autowired
    public void setGetAllSongsByGenresSql(String getAllSongsByGenresSql) {
        this.getAllSongsByGenresSql = getAllSongsByGenresSql;
    }
}
