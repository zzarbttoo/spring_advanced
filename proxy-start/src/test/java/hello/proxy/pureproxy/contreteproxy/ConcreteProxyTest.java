package hello.proxy.pureproxy.contreteproxy;

import hello.proxy.pureproxy.contreteproxy.code.ConcreteClient;
import hello.proxy.pureproxy.contreteproxy.code.ConcreteLogic;
import hello.proxy.pureproxy.contreteproxy.code.TimeProxy;
import org.junit.jupiter.api.Test;

public class ConcreteProxyTest {

    @Test
    void noProxy(){

        ConcreteLogic concreteLogic = new ConcreteLogic();
        ConcreteClient client = new ConcreteClient(concreteLogic);
        client.execute();

    }

    @Test
    void addProxy(){
        //실제 구현체도 proxy 가능
        //다형성에 의해 ConcreteLogic에 timeProxy도 들어갈 수 있다
        ConcreteLogic concreteLogic = new ConcreteLogic();
        TimeProxy timeProxy = new TimeProxy(concreteLogic);
        ConcreteClient client = new ConcreteClient(timeProxy);
        client.execute();
    }
}
