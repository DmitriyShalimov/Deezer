package com.deezer.dao;

import com.deezer.entity.Album;

import java.util.List;

public interface AlbumDao {
    List<Album> getAlbumsByArtistId(int artistId);

    List<Album> getAlbumsByMask(String mask);

    Album getById(Integer id);
}
