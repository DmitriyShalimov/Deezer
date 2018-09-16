package com.deezer.dao.jdbc.mapper

import com.deezer.UnitTest
import com.deezer.entity.User
import org.junit.Test
import org.junit.experimental.categories.Category
import java.sql.ResultSet
import java.sql.SQLException
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.junit.Assert.*

@Category(UnitTest.class)
class UserRowMapperTest {
    @Test
//    before
    void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getInt("id")).thenReturn(1)
        when(resultSet.getString("login")).thenReturn("login")
        when(resultSet.getString("password")).thenReturn("password")
        when(resultSet.getString("salt")).thenReturn("salt")
        def expectedUser = new User(id:1, password: "password", login: "login", salt: "salt")
        //when
        UserRowMapper userRowMapper = new UserRowMapper()

        //then
        def actualResult = userRowMapper.mapRow(resultSet)
        assertEquals(expectedUser, actualResult)
    }
}