package com.deezer.dao.jdbc.mapper

import com.deezer.UnitTest
import com.deezer.entity.Artist
import com.deezer.entity.Genre
import org.junit.Test
import org.junit.experimental.categories.Category

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when
import static org.mockito.Mockito.when

@Category(UnitTest.class)
class GenreRowMapperTest {
    @Test
    void mapRow() {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("title")).thenReturn("title")
        when(resultSet.getString("picture_link")).thenReturn("picture")
        def expectedGenre = {
            id:1
            title: 'title'
            picture:'picture'
        } as Genre

        //when
        GenreRowMapper genreRowMapper = new GenreRowMapper()

        //then
        def actualGenre = genreRowMapper.mapRow(resultSet, 0)
        assertEquals(expectedGenre, actualGenre)
    }
}
