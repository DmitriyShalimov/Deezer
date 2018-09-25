package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.Album;
import com.deezer.entity.Artist;
import com.deezer.entity.SearchResult;
import com.deezer.entity.Song;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SearchResultRowMapper implements RowMapper<SearchResult> {
    private static final String SONG_TYPE = "song";
    private static final String ALBUM_TYPE = "album";

    @Override
    public SearchResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        String search_type = rs.getString("search_type");
        int id = rs.getInt("id");
        String title = rs.getString("title");
        if (SONG_TYPE.equals(search_type)) {
            Song song = new Song();
            song.setId(id);
            song.setTitle(title);
            return song;
        } else if (ALBUM_TYPE.equals(search_type)) {
            Album album = new Album();
            album.setId(id);
            album.setTitle(title);
            return album;
        } else {
            Artist artist = new Artist();
            artist.setId(id);
            artist.setName(title);
            return artist;
        }
    }
}
