package com.deezer.dao.jdbc.mapper;

import com.deezer.entity.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {
    public User mapRow(ResultSet resultSet) throws SQLException {
        return mapRow(resultSet, 0);
    }

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setSalt(resultSet.getString("salt"));
        return user;
    }
}