package com.deezer.dao.jdbc.mapper

import com.deezer.entity.User
import org.junit.Test
import java.sql.ResultSet
import java.sql.SQLException
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when
import static org.junit.Assert.*

class UserRowMapperTest {
    @Test
//    before
    void testMapRow() throws SQLException {
        ResultSet resultSet = mock(ResultSet.class)
        when(resultSet.getString("login")).thenReturn("login")
        when(resultSet.getString("password")).thenReturn("password")
        when(resultSet.getString("salt")).thenReturn("salt")
        User expectedUser = new User()
        expectedUser.setPassword("password")
        expectedUser.setLogin("login")
        expectedUser.setSalt("salt")

        //when
        UserRowMapper userRowMapper = new UserRowMapper()

        //then
        User actualResult = userRowMapper.mapRow(resultSet)
        assertEquals(expectedUser.getPassword(), actualResult.getPassword())
        assertEquals(expectedUser.getSalt(), actualResult.getSalt())
        assertEquals(expectedUser.getLogin(), actualResult.getLogin())
    }
}