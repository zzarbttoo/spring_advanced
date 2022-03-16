package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

@Slf4j
@Aspect
public class AspectV6Advice {

    //@Around는 proceed를 실행하지 않으면 큰 장애가 날 수 있다
//    //ProceedingJoinPoint는 @Around에서만 사용할 수 있다
//    @Around("com.hello.aop.order.aop.Pointcuts.orderAndService()")
//    public Object doTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
//
//
//        try {
//
//            //@Before
//            log.info("[트랜잭션 시작] {} ", joinPoint.getSignature());
//            Object result = joinPoint.proceed();
//            //@AfterReturning
//            log.info("[트랜잭션 커밋] {} ", joinPoint.getSignature());
//            return result;
//        }catch (Exception e){
//            //@AfterThrowing
//            log.info("[트랜잭션 롤백] {}", joinPoint.getSignature());
//            throw e;
//        } finally {
//            //@After
//            log.info("[리소스 릴리즈] {}", joinPoint.getSignature());
//        }
//    }

    @Before("com.hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doBefore(JoinPoint joinPoint){
        log.info("[before] {}", joinPoint.getSignature());
    }

    //return되는 값의 이름을 적어줘야 한다
    //@Around는 result 값을 조작할 수 있었지만 여기서는 불가
    //return type과 맞게 해야 어드바이스 자체 호출 가능(object로 void 대체 가능)
    @AfterReturning(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()", returning = "result")
    public void doReturn(JoinPoint joinPoint, Object result){
        log.info("[return] {} return = {}", joinPoint.getSignature(), result);
    }

    //return되는 값의 이름을 적어줘야 한다
    //@Around는 result 값을 조작할 수 있었지만 여기서는 불가
    @AfterReturning(value = "com.hello.aop.order.aop.Pointcuts.allOrder()", returning = "result")
    public void doReturn(JoinPoint joinPoint, String result){
        log.info("[return2] {} return = {}", joinPoint.getSignature(), result);
    }

    //return되는 값의 이름을 적어줘야 한다
    //@Around는 result 값을 조작할 수 있었지만 여기서는 불가
    @AfterThrowing(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()", throwing = "ex")
    public void doThrowing(JoinPoint joinPoint, Exception ex){
        log.info("[exception] {} message = {}", joinPoint, ex);
    }

    @After(value = "com.hello.aop.order.aop.Pointcuts.orderAndService()")
    public void doAfter(JoinPoint joinPoint){
        log.info("[after] {}", joinPoint.getSignature());
    }




}
