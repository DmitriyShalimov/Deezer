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
    public List<Album> getAlbums(int artistId) {
        return albumDao.getAlbums(artistId);
    }
}
