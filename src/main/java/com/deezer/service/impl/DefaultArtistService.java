package com.deezer.service.impl;

import com.deezer.dao.ArtistDao;
import com.deezer.entity.Artist;
import com.deezer.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultArtistService implements ArtistService {
    private final ArtistDao artistDao;

    @Autowired
    public DefaultArtistService(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public List<Artist> getAll() {
        return artistDao.getAll();
    }
}
