package com.deezer.service;

import com.deezer.entity.SearchResult;

import java.util.List;

public interface SearchService {
    List<SearchResult> getSearchResults(int userId);
}
