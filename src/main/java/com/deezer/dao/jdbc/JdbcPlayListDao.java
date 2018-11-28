package com.deezer.dao.jdbc;

import com.deezer.dao.PlayListDao;
import com.deezer.dao.jdbc.mapper.PlayListRowMapper;
import com.deezer.entity.Access;
import com.deezer.entity.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class JdbcPlayListDao implements PlayListDao {
    private static final int TOP_PLAYLIST_COUNT = 10;
    private static final RowMapper<PlayList> PLAYLIST_ROW_MAPPER = new PlayListRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;

    private String getPlaylistLikeCountSql;
    private String getUserLikeCountForPlaylistSql;
    private String deletePlaylistLikeCountSql;
    private String addPlaylistLikeCountSql;
    private String getTopPlaylistSql;
    private String getAllPlaylistOfUserIdSql;
    private String getAllPublicPlaylistSql;
    private String addNewUserPlaylistSql;
    private String addSongToPlaylistSql;
    private String addSongToPlaylistByPlaylistTitleSql;
    private String addPlaylistPictureSql;
    private String getPlaylistById;
    private String getLikedPlaylistSql;

    @Autowired
    public JdbcPlayListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void addPlaylist(String playlistTitle, Access access, int userId, int songId) {
        logger.info("Start query to add playlist {} to DB", playlistTitle);
        long startTime = System.currentTimeMillis();
        int result = jdbcTemplate.update(addNewUserPlaylistSql, playlistTitle, access, userId);
        if (result == 1) {
            logger.debug("Playlist {} uploaded", playlistTitle);
            logger.debug("Start upload song {} to playlist {}", songId, playlistTitle);
            jdbcTemplate.update(addSongToPlaylistByPlaylistTitleSql, songId, playlistTitle);
            jdbcTemplate.update(addPlaylistPictureSql, playlistTitle);
        }
        logger.info("Finish query to add playlist to DB. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public void addSongToPlaylist(int playlistId, int songId) {
        logger.info("Start query to add song {} playlist {} to DB", songId, playlistId);
        long startTime = System.currentTimeMillis();
        jdbcTemplate.update(addSongToPlaylistSql, playlistId, songId);
        logger.info("Finish query to add playlist to DB. It took {} ms", System.currentTimeMillis() - startTime);
    }

    @Override
    public List<PlayList> getTopPlaylists(int userId) {
        logger.info("Start query to get top playlists from DB");
        long startTime = System.currentTimeMillis();
        List<PlayList> playLists = jdbcTemplate.query(getTopPlaylistSql, PLAYLIST_ROW_MAPPER, userId, TOP_PLAYLIST_COUNT);
        logger.info("Finish query to get top playlists from DB. It took {} ms", System.currentTimeMillis() - startTime);
        return playLists;
    }

    @Override
    public PlayList getById(int id, int userId) {
        logger.info("Start query to get playlist with id {} from DB", id);
        long startTime = System.currentTimeMillis();
        PlayList playList = jdbcTemplate.queryForObject(getPlaylistById, PLAYLIST_ROW_MAPPER, userId, id);
        logger.info("Finish query to get playlist with id {} from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return playList;
    }

    @Override
    public List<PlayList> getLikedPlayLists(int userId) {
        logger.info("Start query to get liked playlists of user {} from DB", userId);
        long startTime = System.currentTimeMillis();
        List<PlayList> playLists = jdbcTemplate.query(getLikedPlaylistSql, PLAYLIST_ROW_MAPPER, userId);
        logger.info("Finish query to get liked playlists of user {} from DB. It took {} ms", userId, System.currentTimeMillis() - startTime);
        return playLists;
    }

    @Override
    public List<PlayList> getAllPublicPlaylists(int userId) {
        logger.info("Start query to get all public playlists of user {} from DB", userId);
        long startTime = System.currentTimeMillis();
        List<PlayList> playLists = jdbcTemplate.query(getAllPublicPlaylistSql, PLAYLIST_ROW_MAPPER, userId);
        logger.info("Finish query to get all public playlists of user {} from DB. It took {} ms", userId, System.currentTimeMillis() - startTime);
        return playLists;
    }

    @Override
    public List<PlayList> getUserPlaylist(int id) {
        logger.info("Start query to get all user {} playlists from DB", id);
        long startTime = System.currentTimeMillis();
        logger.info("Finish query to get all user {} playlists from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return jdbcTemplate.query(getAllPlaylistOfUserIdSql, PLAYLIST_ROW_MAPPER, id, id);
    }

    @Override
    @Transactional
    public void likePlaylist(int playlistId, int userId) {
        if (jdbcTemplate.queryForObject(getUserLikeCountForPlaylistSql, Integer.class, playlistId, userId) != 0) {
            logger.info("Start query to remove like from playlist {} by user {} from DB", playlistId, userId);
            long startTime = System.currentTimeMillis();
            jdbcTemplate.update(deletePlaylistLikeCountSql, playlistId, userId);
            logger.info("Finish query to remove like from playlist {} by user {} from DB. It took {} ms", playlistId, userId, System.currentTimeMillis() - startTime);
        } else {
            logger.info("Start query to add like from playlist {} by user {} from DB", playlistId, userId);
            long startTime = System.currentTimeMillis();
            jdbcTemplate.update(addPlaylistLikeCountSql, playlistId, userId);
            logger.info("Finish query to add like from playlist {} by user {} from DB. It took {} ms", playlistId, userId, System.currentTimeMillis() - startTime);
        }
    }

    @Override
    public Integer getPlaylistLikeCount(int id) {
        logger.info("Start query to get playlist {} like count from DB", id);
        long startTime = System.currentTimeMillis();
        Integer likeCount = jdbcTemplate.queryForObject(getPlaylistLikeCountSql, Integer.class, id);
        logger.info("Finish query to get playlist {} like count from DB. It took {} ms", id, System.currentTimeMillis() - startTime);
        return likeCount;
    }

    @Autowired
    public void setGetPlaylistLikeCountSql(String getPlaylistLikeCountSql) {
        this.getPlaylistLikeCountSql = getPlaylistLikeCountSql;
    }

    @Autowired
    public void setGetUserLikeCountForPlaylistSql(String getUserLikeCountForPlaylistSql) {
        this.getUserLikeCountForPlaylistSql = getUserLikeCountForPlaylistSql;
    }

    @Autowired
    public void setDeletePlaylistLikeCountSql(String deletePlaylistLikeCountSql) {
        this.deletePlaylistLikeCountSql = deletePlaylistLikeCountSql;
    }

    @Autowired
    public void setAddPlaylistLikeCountSql(String addPlaylistLikeCountSql) {
        this.addPlaylistLikeCountSql = addPlaylistLikeCountSql;
    }

    @Autowired
    public void setGetTopPlaylistSql(String getTopPlaylistSql) {
        this.getTopPlaylistSql = getTopPlaylistSql;
    }

    @Autowired
    public void setGetAllPlaylistOfUserIdSql(String getAllPlaylistOfUserIdSql) {
        this.getAllPlaylistOfUserIdSql = getAllPlaylistOfUserIdSql;
    }

    @Autowired
    public void setGetAllPublicPlaylistSql(String getAllPublicPlaylistSql) {
        this.getAllPublicPlaylistSql = getAllPublicPlaylistSql;
    }

    @Autowired
    public void setAddNewUserPlaylistSql(String addNewUserPlaylistSql) {
        this.addNewUserPlaylistSql = addNewUserPlaylistSql;
    }

    @Autowired
    public void setAddSongToPlaylistSql(String addSongToPlaylistSql) {
        this.addSongToPlaylistSql = addSongToPlaylistSql;
    }

    @Autowired
    public void setAddSongToPlaylistByPlaylistTitleSql(String addSongToPlaylistByPlaylistTitleSql) {
        this.addSongToPlaylistByPlaylistTitleSql = addSongToPlaylistByPlaylistTitleSql;
    }

    @Autowired
    public void setAddPlaylistPictureSql(String addPlaylistPictureSql) {
        this.addPlaylistPictureSql = addPlaylistPictureSql;
    }

    @Autowired
    public void setGetPlaylistById(String getPlaylistById) {
        this.getPlaylistById = getPlaylistById;
    }

    @Autowired
    public void setGetLikedPlaylistSql(String getLikedPlaylistSql) {
        this.getLikedPlaylistSql = getLikedPlaylistSql;
    }
}
