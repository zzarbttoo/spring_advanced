package hello.proxy.pureproxy.proxy.code;

import hello.proxy.pureproxy.proxy.decorator.code.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MessageDecorator implements Component {

    private Component component;

    public MessageDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {

        //data -> *****data*****
        log.info("MessageDecorator 실행");
        String result = component.operation(); //realComponent 값이 들어갈 것임
        String decoResult = "*****" + result + "*****";
        log.info("MessageDecorator 꾸미기 적용 전 ={}, 적용 후 = {}", result, decoResult);

        return decoResult;
    }
}
