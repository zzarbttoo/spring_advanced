package hello.proxy.config;

import hello.proxy.app.v1.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//spring bean 수동 등록
@Configuration //config를 등록할 때도 사용하지만 그냥 spring bean을 등록할 때도 사용할 수 있다
public class AppV1Config {

    @Bean
    public OrderControllerV1 orderControllerV1(){
        return new OrderControllerV1Impl(orderServiceV1());
    }

    @Bean
    public OrderServiceV1 orderServiceV1(){
        return new OrderServiceV1Impl(orderRepositoryV1());
    }

    @Bean
    public OrderRepositoryV1 orderRepositoryV1() {
        return new OrderRepositoryV1Impl();
    }
}
