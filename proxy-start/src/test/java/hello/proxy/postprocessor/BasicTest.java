package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BasicTest {

    @Test
    void basicConfig(){
        //안에 들어간 config 정보로 bean을 만든다
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BasicConfig.class); //spring container

        //A는 빈으로 등록된다
        A a = applicationContext.getBean("beanA", A.class);
        a.helloA();

        //B는 빈으로 등록되어있지 않다
        //B bean = applicationContext.getBean(B.class);
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class)); //예외가 터져야 성공

    }

    @Slf4j
    @Configuration
    static class BasicConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }
    }

    @Slf4j
    static class A {
        public void helloA(){
            log.info("hello A");
        }
    }


    @Slf4j
    static class B {
        public void helloB(){
            log.info("hello B");
        }
    }
}
