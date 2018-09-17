package com.deezer.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
@ComponentScan(basePackages = "com.deezer.dao")
@PropertySource(
        value = {"classpath:properties/test.application.properties"})
public class AppTestConfig {
    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Value("${db.url}") String url
            , @Value("${db.username}") String username
            , @Value("${db.password}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
