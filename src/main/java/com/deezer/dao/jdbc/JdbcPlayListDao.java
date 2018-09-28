package com.deezer.dao.jdbc;

import com.deezer.dao.PlayListDao;
import com.deezer.dao.jdbc.mapper.PlayListRowMapper;
import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPlayListDao implements PlayListDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final PlayListRowMapper PLAYLIST_ROW_MAPPER = new PlayListRowMapper();
    private static final String GET_ALL_PLAYLIST_SQL = "SELECT pl.id, pl.title FROM playlist AS pl WHERE pl.access='public'";
    private static final String GET_ALL_PLAYLIST_OF_USER_ID_SQL = "SELECT pl.id, pl.title FROM playlist AS pl\n" +
            "JOIN playlist_user AS plu ON  pl.id=plu.playlist\n" +
            "WHERE plu.user=:userId";
    private static final String ADD_NEW_USER_PLAYLIST_SQL = "WITH new_playlist AS ( " +
            "INSERT INTO playlist (title, \"access\") VALUES (:title,:access)  returning id)" +
            "INSERT INTO playlist_user ( \"user\",playlist)" +
            "VALUES ( :userId,   (SELECT id FROM new_playlist));";
    private static final String ADD_SONG_TO_PLAYLIST_SQL = "INSERT INTO playlist_song (playlist,song) VALUES(:playlistId, :songID)";


    @Autowired
    public JdbcPlayListDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<PlayList> getAll() {
        logger.info("start receiving  all playLists ");
        return namedParameterJdbcTemplate.query(GET_ALL_PLAYLIST_SQL, PLAYLIST_ROW_MAPPER);
    }

    @Override
    public boolean addPlaylist(String playlistTitle, Access access, int userId) {
        logger.info("Start upload playlist {}", playlistTitle);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", userId);
        params.addValue("access", access.getId());
        params.addValue("title", playlistTitle);
       // int result = namedParameterJdbcTemplate.update(ADD_NEW_USER_PLAYLIST_SQL, params);
        logger.info("Playlist {} saved", playlistTitle);
        return true;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        logger.info("Start upload song with id= {} to playlist with id= {}", songId, playlistId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", songId);
        params.addValue("playlist", playlistId);
       // int result = namedParameterJdbcTemplate.update(ADD_SONG_TO_PLAYLIST_SQL, params);
        logger.info("Song with id= {} saved in playlist with id= {}", songId, playlistId);

        return false;
    }

    @Override
    public List<PlayList> getUserPlaylist(Integer id) {
        logger.info("start receiving  albums of user with id {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("userId", id);
        return namedParameterJdbcTemplate.query(GET_ALL_PLAYLIST_OF_USER_ID_SQL, params, PLAYLIST_ROW_MAPPER);
    }
}
