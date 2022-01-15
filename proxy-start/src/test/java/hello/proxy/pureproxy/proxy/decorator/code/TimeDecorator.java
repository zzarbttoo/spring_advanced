package hello.proxy.pureproxy.proxy.decorator.code;

//proxy는 체인이 가능하다
//decorator pattern은 체인을 계속 추가하며 부가 기능들을 추가해나갈 수 있다

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeDecorator implements Component{

    //반복되는 부분은 추상클래스로 뺄 수 있다
    private Component component;

    public TimeDecorator(Component component) {
        this.component = component;
    }

    @Override
    public String operation() {
        log.info("TimeDecorator 실행");
        long startTime = System.currentTimeMillis();

        String result = component.operation();

        long endTime= System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("TimeDecorator 종료 resultTime = {}ms", resultTime);

        return result;
    }
}
