package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class LogTraceFilterHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;
    private final String[] patterns; //filtering을 해서 특정 패턴일 때만 로그를 남김


    public LogTraceFilterHandler(Object target, LogTrace logTrace, String[] patterns) {
        this.target = target;
        this.logTrace = logTrace;
        this.patterns = patterns;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {

        String methodName = method.getName();
        //save, request, reque*, *est

        //spring pattern matching utils
        //pattern 매치가 안 될 경우 실제 로직 바로 호출
        if (!PatternMatchUtils.simpleMatch(patterns, methodName)){
            return method.invoke(target, args);
        }

        TraceStatus status = null;

        try {

            //method 정보에서 class 정보도 꺼내올 수 있다
            //class.method()
            String message = method.getDeclaringClass().getSimpleName()
                    + "." + method.getName()
                    + "()";
            status = logTrace.begin(message);

            //logic 호출
            Object result = method.invoke(target, args);
            logTrace.end(status);
            return result;

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }
    }
}
