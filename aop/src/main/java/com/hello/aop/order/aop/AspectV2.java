package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Slf4j
@Aspect
public class AspectV2 {

    //com.hello.aop.order 패키지와 하위 패키지
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    private void allOrder(){} //pointcut signature(메소드명 + 파라미터), 반환 타입 void

    @Around("allOrder()")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature());

        return joinPoint.proceed();
    }
//
//    @Around("allOrder()")
//    public Object doLog2(ProceedingJoinPoint joinPoint) throws Throwable{
//        log.info("[log] {}", joinPoint.getSignature());
//
//        return joinPoint.proceed();
//    }
}
