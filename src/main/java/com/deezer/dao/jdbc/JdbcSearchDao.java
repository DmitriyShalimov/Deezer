package com.deezer.dao.jdbc;

import com.deezer.dao.SearchDao;
import com.deezer.dao.jdbc.mapper.SearchResultRowMapper;
import com.deezer.entity.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class JdbcSearchDao implements SearchDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final RowMapper<SearchResult> SEARCH_RESULT_ROW_MAPPER = new SearchResultRowMapper();
    private static String GET_SEARCH_OPTIONS = "select s.id, s.title, 'song' as search_type from song s " +
            "union all " +
            "select a.id, a.title, 'album' as search_type from album a " +
            "union all " +
            "select ar.id, ar.name, 'artist' as search_type from artist ar;";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcSearchDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<SearchResult> getSearchResults() {
        logger.info("Getting search results");
        return namedParameterJdbcTemplate.query(GET_SEARCH_OPTIONS, SEARCH_RESULT_ROW_MAPPER);
    }
}
