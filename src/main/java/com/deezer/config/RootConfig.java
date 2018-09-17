package com.deezer.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.deezer",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "com\\.deezer\\.web\\.controller.*"))
public class RootConfig implements WebMvcConfigurer {

    @Bean
    NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Value("${DB_URL}") String url
            , @Value("${DB_USERNAME}") String username
            , @Value("${DB_PASSWORD}") String password) {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return new NamedParameterJdbcTemplate(dataSource);
    }

}
