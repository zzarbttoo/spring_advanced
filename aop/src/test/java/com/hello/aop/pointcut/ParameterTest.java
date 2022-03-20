package com.hello.aop.pointcut;

import com.hello.aop.member.MemberService;
import com.hello.aop.member.annotation.ClassAop;
import com.hello.aop.member.annotation.MethodAop;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootTest
@Import(ParameterTest.ParameterAspect.class)
public class ParameterTest {

    @Autowired
    MemberService memberService;

    @Test
    void success(){
        log.info("memberService Proxy ::: {}", memberService.getClass());
        memberService.hello("helloA");
    }

    @Slf4j
    @Aspect
    static class ParameterAspect{
        @Pointcut("execution(* com.hello.aop.member..*.*(..))")
        private void allMember(){
        }

        @Around("allMember()")
        public Object logArgs1(ProceedingJoinPoint joinPoint) throws Throwable {
            Object arg1 = joinPoint.getArgs()[0];
            log.info("[logArgs1]{}, args ::: {}", joinPoint.getSignature(), arg1);
            return joinPoint.proceed();
        }

        @Around("allMember() && args(arg, ..)")
        public Object logArgs2(ProceedingJoinPoint joinPoint, Object arg) throws Throwable {
            log.info("[logArgs2]{}, args ::: {}", joinPoint.getSignature(), arg);
            return joinPoint.proceed();
        }

        //String 이 아니면 실행 자체가 안됨
        @Before("allMember() && args(arg, ..)")
        public void logArgs3(String arg){
            log.info("[logArgs3] arg ::: {}", arg);
        }

        //spring 컨테이너에 있는 proxy를 호출
        @Before("allMember() && this(obj)") //this는 객체 instance object가 넘어온다
        public void thisArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[this]{}, object::: {}", joinPoint.getSignature(), obj.getClass());
        }

        //실제 target을 받을 수 있다
        @Before("allMember() && target(obj)")
        public void targetArgs(JoinPoint joinPoint, MemberService obj){
            log.info("[target]{}, object::: {}", joinPoint.getSignature(), obj.getClass());
        }

        @Before("allMember() && @target(annotation)")
        public void atTarget(JoinPoint joinPoint, ClassAop annotation){
            log.info("[@target]{}, object::: {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @within(annotation)")
        public void atWithin(JoinPoint joinPoint, ClassAop annotation){
            log.info("[@within]{}, object::: {}", joinPoint.getSignature(), annotation);
        }

        @Before("allMember() && @annotation(annotation)")
        public void atAnnotation(JoinPoint joinPoint, MethodAop annotation){
            log.info("[@annotation]{}, annotationValue::: {}", joinPoint.getSignature(), annotation.value());
        }

    }
}
