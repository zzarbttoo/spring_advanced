package hello.proxy.common.advice;

import lombok.extern.slf4j.Slf4j;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;


//MethodInterceptor가 Interceptor을 상속받고 Interceptor가 Advice를 상속받은 것
@Slf4j
public class TimeAdvice implements MethodInterceptor {


    //proxy factory에서 만들 때 이미 target을 넘겨받게 된다
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {


        //여기에 pointcut 조건을 넣는다면 단일 책임 원칙 벗어남, 그리고 재사용성이 안좋아짐

        log.info("TimeProxy 실행");
        long startTime = System.currentTimeMillis();

        Object result = invocation.proceed(); //이미 target을 찾아 target에 인수를 넘겨 실행을 다 해준다

        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeProxy 종료 resultTime = {}", resultTime);

        return result;
    }
}
