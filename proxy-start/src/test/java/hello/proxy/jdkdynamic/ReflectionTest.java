package hello.proxy.jdkdynamic;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

@Slf4j
public class ReflectionTest {

    @Test
    void reflection0(){
        Hello target = new Hello();

        //공통 로직1 시작
        log.info("start");
        String result1 = target.callA(); //호출하는 메서드가 다름
        log.info("result = {}", result1);
        //공통 로직1 종료

        //공통 로직2 시작
        log.info("start");
        String result2 = target.callB(); //호출하는 메서드가 다름
        log.info("result = {}", result2);
        //공통 로직2 종료
    }

    @Test
    void reflection1() throws Exception{
        //클래스 정보
        //내부에 있을 때는 $사용
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        //클래스 메소드를 string으로 입력해서 호출(차후 파라미터 등으로 넘겨주기 가능)

        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        Object result1 = methodCallA.invoke(target); //target instance의 callA 호출
        log.info("result1 ::: {}", result1);

        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        Object result2 = methodCallB.invoke(target); //target instance의 callB 호출
        log.info("result1 ::: {}", result2);


    }


    //reflexion을 이용해 추상화, 공통화 시키는 것이 가능하다
    //그런데 런타임 동작이기 때문에 컴파일 시점에 오류를 잡을 수 없다-> 웬칸하면 사용 X
    @Test
    void reflection2() throws Exception{
        //클래스 정보
        //내부에 있을 때는 $사용
        Class classHello = Class.forName("hello.proxy.jdkdynamic.ReflectionTest$Hello");
        Hello target = new Hello();

        //callA 메서드 정보
        Method methodCallA = classHello.getMethod("callA");
        dynamicCall(methodCallA, target);

        //callB 메서드 정보
        Method methodCallB = classHello.getMethod("callB");
        dynamicCall(methodCallB, target);

    }

    private void dynamicCall(Method method, Object target) throws Exception{

        log.info("start");
        Object result = method.invoke(target);
        log.info("result = {}", result);


    }

    @Slf4j
    static class Hello{
        public String callA(){
            log.info("callA");
            return "A";
        }
        public String callB(){
            log.info("callB");
            return "B";
        }

    }
}
