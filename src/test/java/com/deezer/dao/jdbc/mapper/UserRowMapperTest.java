package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.User;
import org.junit.Test;
import org.mockito.Mockito;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class UserRowMapperTest {
    @Test
//    before
    public void testMapRow() throws SQLException {
        ResultSet resultSet = Mockito.mock(ResultSet.class);
        Mockito.when(resultSet.getString("login")).thenReturn("login");
        Mockito.when(resultSet.getString("password")).thenReturn("password");
        Mockito.when(resultSet.getString("salt")).thenReturn("salt");
        User expectedUser = new User();
        expectedUser.setPassword("password");
        expectedUser.setLogin("login");
        expectedUser.setSalt("salt");

        //when
        UserRowMapper userRowMapper = new UserRowMapper();

        //then
        User actualResult = userRowMapper.mapRow(resultSet);
        assertEquals(expectedUser.getPassword(), actualResult.getPassword());
        assertEquals(expectedUser.getSalt(), actualResult.getSalt());
        assertEquals(expectedUser.getLogin(), actualResult.getLogin());
    }
}