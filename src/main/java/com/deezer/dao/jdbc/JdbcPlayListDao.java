package com.deezer.dao.jdbc;

import com.deezer.dao.PlayListDao;
import com.deezer.dao.jdbc.mapper.PlayListRowMapper;
import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcPlayListDao implements PlayListDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final int TOP_PLAYLIST_COUNT = 10;
    private static final String USER_ID = "userId";
    private static final String PLAYLIST_ID = "playlistId";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final RowMapper<PlayList> PLAYLIST_ROW_MAPPER = new PlayListRowMapper();

    private static final String SELECT_FROM_PLAYLIST_CLAUSE = "SELECT pl.id, pl.title," +
            " pl.picture, pl.access, pu.id as liked " +
            "FROM playlist AS pl ";
    private static final String LEFT_JOIN_PLAYLIST_USER_CLAUSE = "left join playlist_user pu on pu.user=:userId and pu.playlist=pl.id ";

    private static final String GET_PLAYLIST_LIKE_COUNT_SQL = "SELECT COUNT(*) FROM playlist_user WHERE playlist =:playlistId;";
    private static final String GET_USER_LIKE_COUNT_FOR_PLAYLIST_SQL = "SELECT COUNT(*) FROM playlist_user WHERE playlist =:playlistId AND \"user\"=:userId ;";
    private static final String DELETE_PLAYLIST_LIKE_COUNT_SQL = "DELETE from playlist_user where playlist=:playlistId and \"user\"=:userId";
    private static final String ADD_PLAYLIST_LIKE_COUNT_SQL = "INSERT INTO playlist_user (playlist, \"user\") VALUES (:playlistId,:userId)";
    private static final String GET_TOP_PLAYLIST_SQL = SELECT_FROM_PLAYLIST_CLAUSE +
            LEFT_JOIN_PLAYLIST_USER_CLAUSE +
            " left join (select count(playlist) ct, playlist from playlist_user " +
            " group by playlist) pl_l on pl_l.playlist = pl.id  " +
            "WHERE pl.access='public' " +
            "order by pl_l.ct desc nulls last limit :limit";
    private static final String GET_ALL_PLAYLIST_OF_USER_ID_SQL = SELECT_FROM_PLAYLIST_CLAUSE +
            LEFT_JOIN_PLAYLIST_USER_CLAUSE +
            "WHERE pl.user=:userId";
    private static final String GET_ALL_PUBLIC_PLAYLIST_SQL = SELECT_FROM_PLAYLIST_CLAUSE +
            LEFT_JOIN_PLAYLIST_USER_CLAUSE +
            " WHERE pl.access='public';";
    private static final String ADD_NEW_USER_PLAYLIST_SQL = "insert into playlist(title, \"access\", \"user\") values(:title, :access, :userId)";
    private static final String ADD_SONG_TO_PLAYLIST_SQL = "INSERT INTO playlist_song (playlist,song) VALUES(:playlistId, :songId)";
    private static final String ADD_SONG_TO_PLAYLIST_BY_PLAYLIST_TITLE_SQL = "INSERT INTO playlist_song (playlist,song) (select max(id) as id, :songId from playlist pl where pl.title = :title)";
    private static final String ADD_PLAYLIST_PICTURE_SQL = "with current_pl as(" +
            "select max(id) as id from playlist p " +
            "where p.title = :title " +
            ") update playlist set picture =" +
            " (select max(picture_link) from song s " +
            "join playlist_song ps on ps.song = s.id " +
            "where ps.playlist = (select id from current_pl))" +
            " where id = (select id from current_pl);";
    private static final String GET_PLAYLIST_BY_ID = SELECT_FROM_PLAYLIST_CLAUSE +
            LEFT_JOIN_PLAYLIST_USER_CLAUSE +
            "WHERE pl.id = :id";
    private static final String GET_LIKED_PLAYLIST_SQL = SELECT_FROM_PLAYLIST_CLAUSE +
            "join playlist_user pu on pu.playlist=pl.id " +
            "WHERE pu.user=:userId";

    @Autowired
    public JdbcPlayListDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    @Transactional
    public boolean addPlaylist(String playlistTitle, Access access, int userId, int songId) {
        logger.info("Start upload playlist {}", playlistTitle);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, userId);
        params.addValue("access", access.getId());
        params.addValue("title", playlistTitle);
        params.addValue("songId", songId);
        int result = namedParameterJdbcTemplate.update(ADD_NEW_USER_PLAYLIST_SQL, params);
        if (result == 1) {
            logger.info("Playlist {} uploaded", playlistTitle);
            logger.info("Start upload song {} to playlist {}", songId, playlistTitle);
            namedParameterJdbcTemplate.update(ADD_SONG_TO_PLAYLIST_BY_PLAYLIST_TITLE_SQL, params);
            result = namedParameterJdbcTemplate.update(ADD_PLAYLIST_PICTURE_SQL, params);
        }
        return result == 1;
    }

    @Override
    public boolean addSongToPlaylist(int playlistId, int songId) {
        logger.info("Start upload song with id= {} to playlist with id= {}", songId, playlistId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("songId", songId);
        params.addValue(PLAYLIST_ID, playlistId);
        int result = namedParameterJdbcTemplate.update(ADD_SONG_TO_PLAYLIST_SQL, params);
        logger.info("Song with id= {} saved in playlist with id= {}", songId, playlistId);
        return result == 1;
    }

    @Override
    public List<PlayList> getTopPlaylists(int userId) {
        logger.info("start receiving  top {} playLists ", TOP_PLAYLIST_COUNT);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("limit", TOP_PLAYLIST_COUNT);
        params.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(GET_TOP_PLAYLIST_SQL, params, PLAYLIST_ROW_MAPPER);
    }

    @Override
    public PlayList getById(Integer id, int userId) {
        logger.info("start receiving  playlist {} ", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("id", id);
        params.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.queryForObject(GET_PLAYLIST_BY_ID, params, PLAYLIST_ROW_MAPPER);
    }

    @Override
    public List<PlayList> getLikedPlayLists(int userId) {
        logger.info("start receiving  playLists liked by user {}", userId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(GET_LIKED_PLAYLIST_SQL, params, PLAYLIST_ROW_MAPPER);
    }

    @Override
    public List<PlayList> getAllPublicPlaylists(int userId) {
        logger.info("Start receiving all public playLists");
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, userId);
        return namedParameterJdbcTemplate.query(GET_ALL_PUBLIC_PLAYLIST_SQL, params, PLAYLIST_ROW_MAPPER);
    }

    @Override
    public List<PlayList> getUserPlaylist(Integer id) {
        logger.info("start receiving  albums of user with id {}", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(USER_ID, id);
        return namedParameterJdbcTemplate.query(GET_ALL_PLAYLIST_OF_USER_ID_SQL, params, PLAYLIST_ROW_MAPPER);
    }

    @Override
    @Transactional
    public boolean likePlaylist(int playlistId, int userId) {
        logger.info("Add 'like' to playlist with id {} by user with id {}", playlistId, userId);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PLAYLIST_ID, playlistId);
        params.addValue(USER_ID, userId);
        int result;
        if (namedParameterJdbcTemplate.queryForObject(GET_USER_LIKE_COUNT_FOR_PLAYLIST_SQL, params, Integer.class) != 0) {
            result = namedParameterJdbcTemplate.update(DELETE_PLAYLIST_LIKE_COUNT_SQL, params);
        } else {
            result = namedParameterJdbcTemplate.update(ADD_PLAYLIST_LIKE_COUNT_SQL, params);
        }
        return result == 1;
    }

    @Override
    public String getPlaylistLikeCount(int id) {
        logger.info("Get like count for playlist with id {} ", id);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue(PLAYLIST_ID, id);
        return namedParameterJdbcTemplate.queryForObject(GET_PLAYLIST_LIKE_COUNT_SQL, params, String.class);
    }
}
