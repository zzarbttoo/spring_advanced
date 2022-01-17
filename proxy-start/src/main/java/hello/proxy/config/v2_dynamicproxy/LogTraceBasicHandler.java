package hello.proxy.config.v2_dynamicproxy;

import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

@Slf4j
public class LogTraceBasicHandler implements InvocationHandler {

    private final Object target;
    private final LogTrace logTrace;

    public LogTraceBasicHandler(Object target, LogTrace logTrace) {
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {

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