package com.hello.aop.order.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


//aop로 사용하기 위해서는 따로 등록해줘야 한다(Test 부분에서 Import 하며 등록해줬음)
@Slf4j
@Aspect //컴포넌트 스캔의 대상은 아니다
public class AspectV1 {

    @Around("execution(* com.hello.aop.order..*(..))") //하위 패키지까지 포함
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable{
        log.info("[log] {}", joinPoint.getSignature()); //join point 시그니쳐

        return joinPoint.proceed();
    }
}
