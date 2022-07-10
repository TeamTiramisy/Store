package com.dmdev.logging.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CommonPointcuts {

    @Pointcut("within(com.dmdev.*.service.*Service)")
    public void isServiceLayer() {
    }
}
