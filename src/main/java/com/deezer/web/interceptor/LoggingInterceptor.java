package com.deezer.web.interceptor;


import com.deezer.entity.User;
import com.deezer.web.security.AuthPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.UUID;

public class LoggingInterceptor extends HandlerInterceptorAdapter {
    private static final String USER_KEY = "username";
    private static final String REQUEST_ID_KEY = "request";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        MDC.put(REQUEST_ID_KEY, UUID.randomUUID().toString());
        Principal userPrincipal = request.getUserPrincipal();
        if (userPrincipal != null) {
            User user = ((AuthPrincipal) userPrincipal).getUser();
            if (user != null) {
                MDC.put(USER_KEY, user.getLogin());
            }
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        MDC.clear();
    }
}
