package com.deezer.service.impl;

import com.deezer.dao.GenreDao;
import com.deezer.entity.Genre;
import com.deezer.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultGenreService implements GenreService {
    private final GenreDao genreDao;

    @Autowired
    public DefaultGenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    @Override
    public List<Genre> getAll() {
        return genreDao.getGenres();
    }

    @Override
    public Genre getGenreById(Integer id) {
        return genreDao.getById(id);
    }
}
