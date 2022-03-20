package com.hello.aop.pointcut;

import com.hello.aop.member.MemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.assertThat;

//execution과 비슷한데, type 부분만 가져왔다고 보면 된다
//표현식에 부모타입을 지정하면 안되고, 정확하게 타입이 맞아야 한다
//거의 사용하지 않는다
public class WithinTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class);
    }
    @Test
    void withinExact() {
        pointcut.setExpression("within(com.hello.aop.member.MemberServiceImpl)");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    @Test
    void withinStar() {
        pointcut.setExpression("within(com.hello.aop.member.*Service*)");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
    @Test
    void withinSubPackage() {
        pointcut.setExpression("within(com.hello.aop..*)");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }

    @Test
    @DisplayName("타켓의 타입에만 직접 적용, 인터페이스를 선정하면 안된다.")
    void withinSuperTypeFalse() {
        pointcut.setExpression("within(com.hello.aop.member.MemberService)");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isFalse();
    }
    @Test
    @DisplayName("execution은 타입 기반, 인터페이스를 선정 가능.")
    void executionSuperTypeTrue() {
        pointcut.setExpression("execution(* com.hello.aop.member.MemberService.*(..))");
        assertThat(pointcut.matches(helloMethod,
                MemberServiceImpl.class)).isTrue();
    }
}
