package com.deezer.web.controller;

import com.deezer.entity.SearchResult;
import com.deezer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class SearchController {
    private SearchService searchService;

    @Autowired
    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }

    @GetMapping(value = "search")
    @ResponseBody
    List<SearchResult> getSearchResults(HttpSession session) {
        return searchService.getSearchResults(Util.getUserIdFromHttpSession(session));
    }
}
