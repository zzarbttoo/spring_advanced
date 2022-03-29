package com.hello.aop.exam.aop;

import com.hello.aop.exam.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

@Slf4j
@Aspect
public class RetryAspect {

    //@Around("@annotation(com.hello.aop.exam.annotation.Retry)")
    @Around("@annotation(retry)") //파라미터로 받음
    public Object doRetry(ProceedingJoinPoint joinPoint, Retry retry) throws Throwable {
        log.info("[retry] {} retry ::: {}", joinPoint.getSignature(), retry);

        int maxRetry = retry.value();
        Exception exceptionHolder = null;

        for(int retryCount = 1; retryCount <= maxRetry; retryCount ++) {
            try {
                log.info("[retry] try count = {}/{}", retryCount, maxRetry);
                return joinPoint.proceed(); //한번에 성공하면 나간다
            } catch (Exception e) {

            }
        }

        throw exceptionHolder; //3번 넘어가면 예외 그냥 던져진다
    }

}
