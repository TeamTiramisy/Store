package com.dmdev.logging.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
public class LoggingAspect {

    @Pointcut("com.dmdev.logging.aop.CommonPointcuts.isServiceLayer() &&" +
            "@annotation(org.springframework.transaction.annotation.Transactional)")
    public void hasTransactional(){
    }

    @Before(value = "hasTransactional() && target(service)")
        public void addLogging(JoinPoint joinPoint, Object service) {
            log.info(" before - invoked method {} args {} in class {}",
                    joinPoint.getSignature().getName(), joinPoint.getArgs(), service.getClass().getName());
        }

    @AfterReturning(value = "hasTransactional() && target(service)",
            returning = "result")
    public void addLoggingAfterReturning(JoinPoint joinPoint,Object result, Object service) {
        log.info("after returning - invoked method {} in class {}, result {}",
                joinPoint.getSignature().getName(), service.getClass().getName(), result);
    }

    @AfterThrowing(value = "hasTransactional() && target(service)",
            throwing = "ex")
    public void addLoggingAfterThrowing(JoinPoint joinPoint, Throwable ex, Object service) {
        log.info("after throwing - invoked method {} in class {}, exception {}: {}",
                joinPoint.getSignature().getName(), service.getClass().getName(), ex.getClass(), ex.getMessage());
    }

    @After("hasTransactional() && target(service)")
    public void addLoggingAfterFinally(JoinPoint joinPoint, Object service) {
        log.info("after (finally) - invoked method {} in class {}",
                joinPoint.getSignature().getName(), service.getClass().getName());
    }

}
