package com.deezer.dao.jdbc;

import com.deezer.dao.PlayListDao;
import com.deezer.dao.jdbc.mapper.PlayListRowMapper;
import com.deezer.entity.PlayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcPlayListDao implements PlayListDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private static final PlayListRowMapper PLAYLIST_ROW_MAPPER = new PlayListRowMapper();
    private static final String GET_ALL_PLAYLIST_SQL = "SELECT id,title FROM playlist";

    @Autowired
    public JdbcPlayListDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<PlayList> getAll() {
        logger.info("start receiving  all playLists ");
        return namedParameterJdbcTemplate.query(GET_ALL_PLAYLIST_SQL, PLAYLIST_ROW_MAPPER);
    }
}
