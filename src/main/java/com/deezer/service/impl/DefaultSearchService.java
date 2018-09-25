package com.deezer.service.impl;

import com.deezer.dao.SearchDao;
import com.deezer.entity.SearchResult;
import com.deezer.service.SearchService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DefaultSearchService implements SearchService {
    private SearchDao searchDao;

    public DefaultSearchService(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    @Override
    public List<SearchResult> getSearchResults() {
        return searchDao.getSearchResults();
    }
}
