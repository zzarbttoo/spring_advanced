package hello.proxy.cglib;

import hello.proxy.cglib.code.TimeMethodInterceptor;
import hello.proxy.common.service.ConcreteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.cglib.proxy.Enhancer;

@Slf4j
public class CglibTest {


    @Test
    void cglib(){
        ConcreteService target = new ConcreteService();

        //cglib를 만들어준다
        Enhancer enhancer = new Enhancer();
        //구체 클래스를 상속 받은 proxy를 만들어줘야한다
        enhancer.setSuperclass(ConcreteService.class);
        enhancer.setCallback(new TimeMethodInterceptor(target));
        //Object proxy = enhancer.create();//proxy 생성
        ConcreteService proxy = (ConcreteService) enhancer.create();//proxy 부모 객체가 ConcreteService

        log.info("targetClass :: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());

        proxy.call();

    }
}
