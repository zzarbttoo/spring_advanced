package hello.proxy.jdkdynamic;

import hello.proxy.jdkdynamic.code.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Proxy;

@Slf4j
public class JdkDynamicProxyTest {

    @Test
    void dynamicA(){
        AInterface target = new AImpl();

        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        //proxy 생성
        //프록시 생성 위치, 들어가는 인터페이스, 프록시 핸들러(로직)

        //Object proxy = Proxy
        //반환 타입이 AInterface여서 cast해서 사용 가능
       AInterface proxy = (AInterface) Proxy
                .newProxyInstance(AInterface.class.getClassLoader()
                , new Class[]{AInterface.class}, handler);

       proxy.call(); //AInterface에서 call이 있어서 호출 가능
        log.info("targetClass ::: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());


    }

    //로직 하나만 정의하면 계속 동적으로 사용할 수 있다
    @Test
    void dynamicB(){

        BInterface target = new BImpl();
        TimeInvocationHandler handler = new TimeInvocationHandler(target);

        BInterface proxy = (BInterface) Proxy
                .newProxyInstance(BInterface.class.getClassLoader()
                        , new Class[]{BInterface.class}, handler);

        proxy.call();
        log.info("targetClass ::: {}", target.getClass());
        log.info("proxyClass ::: {}", proxy.getClass());


    }

}
