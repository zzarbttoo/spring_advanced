package com.hello.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;

@Slf4j
public class AspectV5Order {

    //순서 적용이 @Aspect 적용 단위(class)로 되기 때문에 따로 class를 만들어서 순서 지정
    @Aspect
    @Order(2)
    public static class LogAspect{

        @Around("com.hello.aop.Pointcuts.allOrder()")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
            log.info("[log] {}", joinPoint.getSignature());

            return joinPoint.proceed();
        }

    }

    @Aspect
    @Order(1)
    public static class TxAspect{

        //hello.aop.order 패키지와 하위 패키지이면서 클래스 이름 패턴이 *Service
        @Around("com.hello.aop.Pointcuts.orderAndService()")
        public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{

            try {
                log.info("[트랜잭션 시작] {} ", joinPoint.getSignature());
                Object result = joinPoint.proceed();
                log.info("[트랜잭션 커밋] {} ", joinPoint.getSignature());
                return result;
            }catch (Exception e){
                log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
                throw e;
            } finally {
                log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
            }
        }
    }

}
