package com.hello.aop.pointcut;

import com.hello.aop.member.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@Import(AtAnnotationTest.AtAnnotationAspect.class) //등록!
@SpringBootTest
public class AtAnnotationTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService proxy = {}", memberService.getClass());
        memberService.hello("helloA");
    }


    //MethodAop 라는 annotation이 적용되어있는 method가 실행이 된다
    @Slf4j
    @Aspect
    static class AtAnnotationAspect{
        @Around("@annotation(com.hello.aop.member.annotation.MethodAop)")
        public Object doAtAnnotation(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[@annotation] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
