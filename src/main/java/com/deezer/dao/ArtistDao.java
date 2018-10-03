package com.deezer.dao;

import com.deezer.entity.Artist;

import java.util.List;

public interface ArtistDao {
    List<Artist> getAll();

    List<Artist> getArtistsByMask(String mask);

    Artist getById(int id);
}
