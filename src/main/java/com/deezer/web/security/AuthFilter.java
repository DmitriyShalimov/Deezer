package com.deezer.web.security;

import com.deezer.entity.User;
import com.deezer.service.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Service
public class AuthFilter implements Filter {
    private static final String USER_TOKEN = "User-Token";
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private SecurityService securityService;


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String token = httpServletRequest.getHeader(USER_TOKEN);
        if (token != null) {
            Optional<User> userByToken = securityService.getUserByToken(token);
            if (userByToken.isPresent()) {
                User user = userByToken.get();
                log.info("User {} is added to principal", user);
                filterChain.doFilter(new UserRequestWrapper(httpServletRequest, user), servletResponse);
            } else {
                log.info("User token {} is invalid", token);
                filterChain.doFilter(new UserRequestWrapper(httpServletRequest, null), servletResponse);
            }
        } else {
            log.info("User is not logged in");
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }


    @Override
    public void init(FilterConfig filterConfig) {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }
}