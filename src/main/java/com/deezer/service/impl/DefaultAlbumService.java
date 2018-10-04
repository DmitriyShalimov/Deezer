package com.deezer.service.impl;

import com.deezer.dao.AlbumDao;
import com.deezer.entity.Album;
import com.deezer.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DefaultAlbumService implements AlbumService {
    private final AlbumDao albumDao;

    @Autowired
    public DefaultAlbumService(AlbumDao albumDao) {
        this.albumDao = albumDao;
    }

    @Override
    public List<Album> getAlbumsByArtistId(int artistId) {
        return albumDao.getAlbumsByArtistId(artistId);
    }

    @Override
    public List<Album> getAlbumsByMask(String mask) {
        return albumDao.getAlbumsByMask(mask);
    }

    @Override
    public Album getById(int id) {
        return albumDao.getById(id);
    }
}
