package com.deezer.dao.jdbc.mapper

import com.deezer.UnitTest
import com.deezer.entity.Artist
import org.junit.Test
import org.junit.experimental.categories.Category

import java.sql.ResultSet

import static org.junit.Assert.assertEquals
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

@Category(UnitTest.class)
class ArtistRowMapperTest {
    @Test
    void mapRow() {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("name")).thenReturn("name")
        when(resultSet.getString("picture")).thenReturn("picture")
        def expectedArtist = {
            id:1
            name: 'name'
            picture:'picture'
        } as Artist

        //when
        ArtistRowMapper artistRowMapper = new ArtistRowMapper()

        //then
        def actualAritst = artistRowMapper.mapRow(resultSet, 0)
        assertEquals(expectedArtist, actualAritst)
    }
}
