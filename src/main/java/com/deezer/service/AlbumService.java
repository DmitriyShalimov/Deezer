package com.deezer.service;

import com.deezer.entity.Album;

import java.util.List;

public interface AlbumService {
    List<Album> getAlbums(int artistId);
}
