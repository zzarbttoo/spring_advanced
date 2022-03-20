package com.hello.aop.pointcut;

import com.hello.aop.order.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

//bean 이름으로 매칭(bean이 확정적일 때 사용하면 효과)
//spring에서만 사용할 수 있다

@Slf4j
@SpringBootTest
@Import(BeanTest.BeanAspect.class) //Aspect 등록
public class BeanTest {

    @Autowired
    OrderService orderService;

    @Test
    void success(){
        orderService.orderItem("itemA");
    }

    @Aspect
    static class BeanAspect{
        @Around("bean(orderService) || bean(*Repository)")
        public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
            log.info("[bean] {}", joinPoint.getSignature());
            return joinPoint.proceed();
        }
    }
}
