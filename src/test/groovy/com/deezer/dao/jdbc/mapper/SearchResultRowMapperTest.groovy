package com.deezer.dao.jdbc.mapper

import com.deezer.UnitTest
import com.deezer.entity.Album
import com.deezer.entity.Artist
import com.deezer.entity.Song
import org.junit.Test
import org.junit.experimental.categories.Category

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@Category(UnitTest.class)
class SearchResultRowMapperTest {
    @Test
    void mapRow() {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("title")).thenReturn("title")

        //song
        when(resultSet.getString("search_type")).thenReturn("song")
        def expectedSong = {
            id: 1
            title: 'title'
        } as Song
        SearchResultRowMapper searchResultRowMapper = new SearchResultRowMapper()
        assertEquals(expectedSong, searchResultRowMapper.mapRow(resultSet, 0))

        //album
        when(resultSet.getString("search_type")).thenReturn("album")
        def expectedAlbum = {
            id: 1
            title: 'title'
        } as Album
        assertEquals(expectedAlbum, searchResultRowMapper.mapRow(resultSet, 0))

        //artist
        when(resultSet.getString("search_type")).thenReturn("album")
        def expectedArtist = {
            id: 1
            name: 'title'
        } as Artist
        assertEquals(expectedArtist, searchResultRowMapper.mapRow(resultSet, 0))
    }
}
