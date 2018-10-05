package com.deezer.dao.jdbc;

import com.deezer.config.logging.LogExecutionTime;
import com.deezer.dao.UserDao;
import com.deezer.dao.jdbc.mapper.UserRowMapper;
import com.deezer.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

@Repository
@LogExecutionTime
public class JdbcUserDao implements UserDao {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private static final UserRowMapper USER_ROW_MAPPER = new UserRowMapper();
    private static final String ADD_NEW_USER_SQL = "INSERT INTO \"user\" (login,password,salt) VALUES(:login, :password, :salt);";
    private static final String GET_USER_BY_LOGIN = "SELECT id,login, password,salt FROM \"user\"  WHERE login=:login";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public JdbcUserDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public void add(User user) {
        logger.info("Start upload user {}", user.getLogin());
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", user.getLogin());
        params.addValue("password", user.getPassword());
        params.addValue("salt", user.getSalt());
        namedParameterJdbcTemplate.update(ADD_NEW_USER_SQL, params);
        logger.info("User {} saved", user.getLogin());
    }

    @Override
    public Optional<User> getByLogin(String login) {
        logger.info("Start getting user {}", login);
        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("login", login);
        try {
            User user = namedParameterJdbcTemplate.queryForObject(GET_USER_BY_LOGIN, params, USER_ROW_MAPPER);
            logger.info("User {} was found", login);
            return Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            logger.info("User {} was not found", login);
        }
        return Optional.empty();
    }

}
