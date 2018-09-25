package com.deezer.dao.jdbc.mapper

import com.deezer.UnitTest
import com.deezer.entity.Album
import com.deezer.entity.Artist
import org.junit.Test
import org.junit.experimental.categories.Category

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@Category(UnitTest.class)
class AlbumRowMapperTest {
    @Test
    void mapRow() {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("title")).thenReturn("title")
        when(resultSet.getString("picture_link")).thenReturn("picture_link")
        when(resultSet.getString("artist_name")).thenReturn('artist')
        def expectedAlbum = {
            id:1
            artist: new Artist(name: 'artist')
            title: 'title'
            picture:'picture_link'
        } as Album

        //when
        AlbumRowMapper albumRowMapper = new AlbumRowMapper()

        //then
        def actualAlbum = albumRowMapper.mapRow(resultSet, 0)
        assertEquals(expectedAlbum, actualAlbum)
    }
}
