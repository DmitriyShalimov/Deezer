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
class SongDetailsRowMapperTest {
    @Test
    void mapRow() {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("title")).thenReturn("title")
        when(resultSet.getString("track_url")).thenReturn("track_url")
        def expectedSong = new Song(id: 1, title: 'title', url: 'track_url', artist: new Artist(id:0), album: new Album(id:0))

        //when
        SongDetailsRowMapper songRowMapper = new SongDetailsRowMapper()

        //then
        def actualSong = songRowMapper.mapRow(resultSet, 0)
        assertEquals(expectedSong, actualSong)
    }
}
