package hello.proxy.config.v1_proxy;

import hello.proxy.app.v1.*;
import hello.proxy.config.v1_proxy.interface_proxy.OrderControllerInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderRepositoryInterfaceProxy;
import hello.proxy.config.v1_proxy.interface_proxy.OrderServiceInterfaceProxy;
import hello.proxy.trace.logtrace.LogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//실제 객체를 등록하면 안되기 때문에 proxy를 실제 객체 대신에 등록하도록 했다
@Configuration
public class InterfaceProxyConfig {

    @Bean
    public OrderControllerV1 orderController(LogTrace logTrace){

        //진짜 service가 아니라 proxy를 호출해야한다
        OrderControllerV1Impl controllerImpl = new OrderControllerV1Impl(orderService(logTrace)); //구현체
        //구현체 대신 proxy를 호출해야한다
        return new OrderControllerInterfaceProxy(controllerImpl, logTrace); //proxy 참조
    }

    @Bean
    public OrderServiceV1 orderService(LogTrace logTrace){

        OrderServiceV1Impl serviceImple = new OrderServiceV1Impl(orderRepository(logTrace));
        return new OrderServiceInterfaceProxy(serviceImple, logTrace);
    }

    @Bean
    public OrderRepositoryV1 orderRepository(LogTrace logTrace) {

        OrderRepositoryV1Impl repositoryImpl = new OrderRepositoryV1Impl();
        return new OrderRepositoryInterfaceProxy(repositoryImpl, logTrace);
    }

}
