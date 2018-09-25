package com.deezer.service;

import com.deezer.entity.Artist;

import java.util.List;

public interface ArtistService {
    List<Artist> getAll();

    List<Artist> getArtistsByMask(String mask);
}
