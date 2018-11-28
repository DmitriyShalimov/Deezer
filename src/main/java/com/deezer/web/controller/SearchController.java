package com.deezer.web.controller;

import com.deezer.entity.SearchResult;
import com.deezer.service.SearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<SearchResult> getSearchResults(HttpSession session) {
        logger.info("Sending request to get all public playlists");
        long start = System.currentTimeMillis();
        List<SearchResult> searchResults = searchService.getSearchResults(Util.getUserIdFromHttpSession(session));
        logger.info("Search results are {}. It took {} ms", searchResults, System.currentTimeMillis() - start);
        return searchResults;
    }
}
