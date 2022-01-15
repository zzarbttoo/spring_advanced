package hello.proxy.pureproxy.proxy.decorator.code;

import lombok.extern.slf4j.Slf4j;

/*
* 프록시 패턴은 접근제어를 수행한다
* 데코레이터 패턴은 프록시 패턴에 부가기능을 더해서 수행할 수 있도록 한다
* ex) 요청, 응답 값 변화, 실행시간 측정 로그 남긴다
* */
@Slf4j
public class DecoratorPatternClient {

    private Component component;

    public DecoratorPatternClient(Component component) {
        this.component = component;
    }

    public void execute(){
        String result = component.operation();
        log.info("result ::: {} ", result);

    }
}

