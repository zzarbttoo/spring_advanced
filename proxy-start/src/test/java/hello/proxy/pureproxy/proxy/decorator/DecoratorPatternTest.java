package hello.proxy.pureproxy.proxy.decorator;

import hello.proxy.pureproxy.proxy.code.MessageDecorator;
import hello.proxy.pureproxy.proxy.decorator.code.Component;
import hello.proxy.pureproxy.proxy.decorator.code.DecoratorPatternClient;
import hello.proxy.pureproxy.proxy.decorator.code.RealComponent;
import hello.proxy.pureproxy.proxy.decorator.code.TimeDecorator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class DecoratorPatternTest {

    @Test
    void noDecorator(){
        RealComponent realComponent = new RealComponent();
        DecoratorPatternClient client = new DecoratorPatternClient(realComponent);
        client.execute();
    }

    @Test
    void decorator1(){
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        DecoratorPatternClient client = new DecoratorPatternClient(messageDecorator);
        client.execute();
    }

    //proxy, decorator 차이는 intent의 차이!
    //접근 제어 목적이면 proxy, 기능 추가 목적이면 decorator
    @Test
    void decorator2() {
        //proxy chain
        Component realComponent = new RealComponent();
        Component messageDecorator = new MessageDecorator(realComponent);
        Component timeDecorator = new TimeDecorator(messageDecorator);
        DecoratorPatternClient client = new DecoratorPatternClient(timeDecorator);
        client.execute();
    }
}

