package com.deezer.service;

import com.deezer.entity.Genre;

import java.util.List;

public interface GenreService {
    List<Genre> getAll();

    Genre getGenreById(int id);
}
