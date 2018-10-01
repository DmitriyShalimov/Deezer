package com.deezer.dao;

import com.deezer.entity.Genre;

import java.util.List;

public interface GenreDao {
    List<Genre> getGenres();

    Genre getById(Integer id);
}

