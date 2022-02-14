package hello.proxy.postprocessor;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanPostProcessorTest {

    @Test
    void basicConfig(){
        //안에 들어간 config 정보로 bean을 만든다
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(BeanPostProcessorConfig.class); //spring container

        //A는 빈으로 등록된다
        //A a = applicationContext.getBean("beanA", A.class);
        //a.helloA();

        //beanA 이름으로 B 객체가 빈으로 등록된다
        B b = applicationContext.getBean("beanA", B.class);
        b.helloB();

        //B는 빈으로 등록되어있지 않다
        //B bean = applicationContext.getBean(B.class);

        //Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(B.class)); //예외가 터져야 성공

        //A는 빈으로 등록되지 않는다
        Assertions.assertThrows(NoSuchBeanDefinitionException.class, () -> applicationContext.getBean(A.class));

    }

    @Slf4j
    @Configuration
    static class BeanPostProcessorConfig{
        @Bean(name = "beanA")
        public A a(){
            return new A();
        }

        @Bean
        public AToBPostProcessor helloPostProcessor(){
            return new AToBPostProcessor();
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


    //bean 후처리기로 A -> B 객체로 바꾸고 등록 시킬 것
    //빈을 조작하고 변경할 수 있는 후킹 포인트
    //컴포넌트 스캔의 대상이 되는 빈들은 중간에 조작할 방법이 없는데 빈 후처리기를 사용하면 개발자가 등록하는 모든 빈을 중간에 조작할 수 있다
    //-> 빈 객체를 프록시 객체로 교체하는 것도 가능하다
    //참고로 spring @postconstruct도 이걸 사용한다
    @Slf4j
    static class AToBPostProcessor implements BeanPostProcessor{

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("beanName = {} bean = {} ", beanName, bean); //후처리 진행
            if (bean instanceof A){ //A를 B로 바꿔치기 해서 등록
                return new B();
            }
            return bean;
        }
    }
}
