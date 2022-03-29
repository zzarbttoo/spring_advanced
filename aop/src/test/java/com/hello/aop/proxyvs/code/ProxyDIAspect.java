package com.hello.aop.proxyvs.code;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Before;

@Slf4j
public class ProxyDIAspect {

    @Before("execution(* com.hello.aop..*.*(..))")
    public void doTrace(JoinPoint joinPoint){
      log.info("[proxyDIAdvice] {}", joinPoint.getSignature());
    }
}
