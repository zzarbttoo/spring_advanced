package hello.proxy.advisor;

import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.NameMatchMethodPointcut;

import java.lang.reflect.Method;

@Slf4j
public class AdvisorTest {

    @Test
    void advisorTest1(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Pointcut.TRUE  : 항상 참인 pointcut
        //advisor에 pointcut, advice 집어넣음(advisor은 하나의 포인트 컷과 어드바이스를 가진다)
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();

        /*
        * proxyFactory.advice(new TimeAdvice())와 같이 advisor이 아니라 advice를 바로 적용한 경우 내부 구조를 살펴보면
        * 위와 같이 Pointcut.True로 되어있는 것을 확인할 수 있다
        * */
    }

    @Test
    @DisplayName("직접 만든 포인트컷")
    void advisorTest2(){
        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);

        // Pointcut.TRUE  : 항상 참인 pointcut
        //advisor에 pointcut, advice 집어넣음(advisor은 하나의 포인트 컷과 어드바이스를 가진다)
        //DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(Pointcut.TRUE, new TimeAdvice());
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(new MyPointCut(), new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();

        /*
         * proxyFactory.advice(new TimeAdvice())와 같이 advisor이 아니라 advice를 바로 적용한 경우 내부 구조를 살펴보면
         * 위와 같이 Pointcut.True로 되어있는 것을 확인할 수 있다
         * */
    }

    static class MyPointCut implements Pointcut{

        @Override
        public ClassFilter getClassFilter() {
            return ClassFilter.TRUE;
        }

        @Override
        public MethodMatcher getMethodMatcher() {
            return new MyMethodMatcher();
        }
    }

    static class MyMethodMatcher implements MethodMatcher{

        private String matchName = "save";

        //성능 향상 가능
        @Override
        public boolean matches(Method method, Class<?> targetClass) {
            boolean result = method.getName().equals(matchName);

            log.info("포인트컷 호출 method = {} targetClass = {}", method.getName(), targetClass);
            log.info("포인트 컷 결과 result {}", result);
            return result;
        }

        //isRuntime이 참이면 인수들이 넘어가는 matches가 호출이 된다
        //false면 위의 matches가 호출된다
        //정적인 정보들은 캐싱을 할 수 있지만, args같은 것은 캐싱할 수 없기 때문에 isRuntime으로 구분
        @Override
        public boolean isRuntime() {
            return false; //true면 캐싱 X
        }

        @Override
        public boolean matches(Method method, Class<?> targetClass, Object... args) {
            return false;
        }
    }


    @Test
    @DisplayName("스프링이 제공하는 포인트컷")
    void advisorTest3(){

        String matchName = "save";

        ServiceInterface target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        NameMatchMethodPointcut pointcut = new NameMatchMethodPointcut();//spring이 제공하는 포인트컷
        pointcut.setMappedName(matchName); //여기에 맞는 method 명을 넣는다
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor(pointcut, new TimeAdvice());
        proxyFactory.addAdvisor(advisor);
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        proxy.save();
        proxy.find();

        /*
        * 여러 포인트 컷들이 있지만 실무에서는 AspectJExpressionPointcut을 사용하게 된다
        * */

    }
}
