package com.deezer.dao.jdbc;

import com.deezer.dao.UserDao;
import com.deezer.dao.jdbc.mapper.UserRowMapper;
import com.deezer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final JdbcTemplate jdbcTemplate;
    private String addNewUserSql;
    private String getUserByLogin;

    @Autowired
    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(User user) {
        logger.info("Start query to add user {} to DB", user);
        long startTime = System.currentTimeMillis();
        jdbcTemplate.update(addNewUserSql, user.getLogin(), user.getPassword(), user.getSalt());
        logger.info("Finish query to add user {} to DB. It took {} ms", user, System.currentTimeMillis() - startTime);
    }

    @Override
    public Optional<User> getByLogin(String login) {
        logger.info("Start query to get user by login {} from DB", login);
        long startTime = System.currentTimeMillis();
        try {
            User user = jdbcTemplate.queryForObject(getUserByLogin, USER_ROW_MAPPER, login);
            logger.info("Finish query to get user by login {} from DB. User was found. It took {} ms", login, System.currentTimeMillis() - startTime);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            logger.info("Finish query to get user by login {} from DB. User was not found. It took {} ms", login, System.currentTimeMillis() - startTime);
        }
        return Optional.empty();
    }

    @Autowired
    public void setAddNewUserSql(String addNewUserSql) {
        this.addNewUserSql = addNewUserSql;
    }

    @Autowired
    public void setGetUserByLogin(String getUserByLogin) {
        this.getUserByLogin = getUserByLogin;
    }
}
