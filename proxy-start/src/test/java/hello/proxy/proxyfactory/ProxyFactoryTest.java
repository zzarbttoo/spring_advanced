package hello.proxy.proxyfactory;


import hello.proxy.common.advice.TimeAdvice;
import hello.proxy.common.service.ConcreteService;
import hello.proxy.common.service.ServiceImpl;
import hello.proxy.common.service.ServiceInterface;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.aop.support.AopUtils;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ProxyFactoryTest {

    @Test
    @DisplayName("인터페이스가 있으면 JDK 동적 프록시 사용")
    void interfaceProxy(){
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.setProxyTargetClass(true); //항상 CGLIB 기반으로 만들 수 있도록 한다
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass ::: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());

        proxy.save();

        //proxy factory를 이용해서 만들었을 때 사용 가능
        //proxy factory가 인터페이스가 있을 경우 자동으로 JDK 동적 프록시, 구체 클래스만 있다면 CglibProxy
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

        log.info("proxyClass ::: {} ", proxy.getClass());

    }


    @Test
    @DisplayName("구체 클래스만 있으면 CGLIB 사용")
    void concreteProxy(){
        ConcreteService target = new ConcreteService(); //구체클래스
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ConcreteService proxy = (ConcreteService) proxyFactory.getProxy();

        log.info("targetClass ::: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());

        proxy.call();

        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isFalse();
        assertThat(AopUtils.isCglibProxy(proxy)).isTrue();

        log.info("proxyClass ::: {} ", proxy.getClass());

    }


    @Test
    @DisplayName("proxyTargetClass 옵션을 사용하면 인터페이스가 있어도 CGLIB를 사용하고 클래스 기반 프록시 사용")
    void proxyTargetClass(){
        ServiceImpl target = new ServiceImpl();
        ProxyFactory proxyFactory = new ProxyFactory(target);
        proxyFactory.addAdvice(new TimeAdvice());
        ServiceInterface proxy = (ServiceInterface) proxyFactory.getProxy();

        log.info("targetClass ::: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());

        proxy.save();

        //proxy factory를 이용해서 만들었을 때 사용 가능
        //proxy factory가 인터페이스가 있을 경우 자동으로 JDK 동적 프록시, 구체 클래스만 있다면 CglibProxy
        assertThat(AopUtils.isAopProxy(proxy)).isTrue();
        assertThat(AopUtils.isJdkDynamicProxy(proxy)).isTrue();
        assertThat(AopUtils.isCglibProxy(proxy)).isFalse();

        log.info("proxyClass ::: {} ", proxy.getClass());

    }

}
