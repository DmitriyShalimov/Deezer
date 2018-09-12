package com.deezer.dao.jdbc;

import com.deezer.dao.SongDao;
import com.deezer.entity.Song;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcSongDao implements SongDao {

    @Override
    public Song getSong(int id) {
        Song song =new Song();
        song.setUrl("https://docs.google.com/uc?export=download&id=1QIbBCdPsuw4PfCbjUuHskETSICSH5fA3");
        return song;
    }
}
