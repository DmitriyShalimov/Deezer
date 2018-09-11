package com.deezer.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan(basePackages = "com.deezer",
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern="com\\.deezer\\.web\\.controller.*"))
public class RootConfig implements WebMvcConfigurer {

}
