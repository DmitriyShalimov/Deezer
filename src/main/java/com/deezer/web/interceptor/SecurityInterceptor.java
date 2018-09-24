package com.deezer.web.interceptor;


import com.deezer.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityInterceptor extends HandlerInterceptorAdapter {
    private static final String LOGGED_USER_ATTRIBUTE = "loggedUser";
    private static final String USER_KEY = "username";
    private static final String REDIRECT_URI = "/login";
    private static final long MAX_INACTIVE_SESSION_TIME = 24 * 60 * 60 * 1000;// 24 hours

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User loggedUser = (User) session.getAttribute(LOGGED_USER_ATTRIBUTE);
        if (loggedUser == null) {
            logger.info("User in not logged. Redirecting to login");
            response.sendRedirect(REDIRECT_URI);
            return false;
        } else {
            long lastAccessedTime = session.getLastAccessedTime();
            if (System.currentTimeMillis() - lastAccessedTime > MAX_INACTIVE_SESSION_TIME) {
                session.removeAttribute(LOGGED_USER_ATTRIBUTE);
                MDC.clear();
                logger.info("Session for user {} has expired. Redirecting to login", loggedUser.getLogin());
                response.sendRedirect(REDIRECT_URI);
                return false;
            } else {
                MDC.put(USER_KEY, loggedUser.getLogin());
                logger.info("Session for user {} is valid", loggedUser.getLogin());
                return true;
            }
        }
    }
}
