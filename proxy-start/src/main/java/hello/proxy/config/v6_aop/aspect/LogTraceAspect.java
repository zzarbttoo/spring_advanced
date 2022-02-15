package hello.proxy.config.v6_aop.aspect;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;


@Slf4j
@Aspect //advice랑 pointcut을 annotation으로 편리하게 생성
public class LogTraceAspect {

    private final LogTrace logTrace;

    public LogTraceAspect(LogTrace logTrace) {
        this.logTrace = logTrace;
    }

    //advisor
    @Around("execution(* hello.proxy.app..*(..))") //pointcut
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{ //advice 로직

        //joinpoint에는 실제 호출 대상, 전달 인자, 어떤 객체와 메서드가 호출되었는지 정보 등이 포함됨

        TraceStatus status = null;
        try {

            String message = joinPoint.getSignature().toShortString();
            status = logTrace.begin(message);

            //logic 호출
            Object result = joinPoint.proceed(); //실제 대상 호출
            logTrace.end(status);
            return result;

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }


    }

}
