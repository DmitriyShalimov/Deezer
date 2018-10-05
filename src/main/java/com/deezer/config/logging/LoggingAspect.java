package com.deezer.config.logging;


import ch.qos.logback.classic.Logger;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private final Logger logger =
            (Logger) LoggerFactory.getLogger(getClass());

    @Around("@within(com.deezer.config.logging.LogExecutionTime) || @annotation(com.deezer.config.logging.LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object value;
        if (logger.isDebugEnabled()) {
            long start = System.currentTimeMillis();
            String method = proceedingJoinPoint.getSignature().getName();
            logger.debug("Invoking method {}", method);
            value = proceedingJoinPoint.proceed();
            long end = System.currentTimeMillis();
            logger.debug("Method {} execution time = {}ms", method, end - start);
        } else {
            value = proceedingJoinPoint.proceed();
        }
        return value;
    }
}

