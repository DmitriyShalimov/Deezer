package com.deezer.dao;

import com.deezer.entity.SearchResult;

import java.util.List;

public interface SearchDao {
    List<SearchResult> getSearchResults();
}
