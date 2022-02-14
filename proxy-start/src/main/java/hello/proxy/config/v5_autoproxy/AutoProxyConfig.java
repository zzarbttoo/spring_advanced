package hello.proxy.config.v5_autoproxy;

import hello.proxy.config.AppV1Config;
import hello.proxy.config.AppV2Config;
import hello.proxy.config.v3_proxyfactory.advice.LogTraceAdvice;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//postProcessor은 자동으로 등록되기 때문에 advisor만 등록하면 된다(빈 후처리기는 등록 필요 X)
@Configuration
@Import({AppV1Config.class, AppV2Config.class})
public class AutoProxyConfig {

    //@Bean
    public Advisor advisor1(LogTrace logTrace) {

        //pointcut
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();
        pointcut.setMappedNames("request*", "order*", "save*");

        //요청하지 않은 것들도 같이 pointcut이 걸림 -> 정밀한 pointcut 필요(aspectJ expression)

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    //@Bean
    public Advisor advisor2(LogTrace logTrace) {

        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* hello.proxy.app..*(..))"); //위치에 있어야 proxy 적용 대상이 된다

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

    @Bean
    public Advisor advisor3(LogTrace logTrace) {

        //pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        //pointcut.setExpression("execution(* hello.proxy.app..*(..))"); //위치에 있어야 proxy 적용 대상이 된다
        pointcut.setExpression("execution(* hello.proxy.app..*(..)) && !execution(* hello.proxy.app..noLog(..))"); //위치에 있어야 proxy 적용 대상이 된다
        //noLog가 아닌 조건을 추가로 걸었다

        //advice
        LogTraceAdvice advice = new LogTraceAdvice(logTrace);
        return new DefaultPointcutAdvisor(pointcut, advice);
    }

}
