package com.hello.aop.pointcut;

import com.hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;

import java.lang.reflect.Method;

import static org.assertj.core.api.Assertions.*;

@Slf4j
public class ExecutionTest {

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    Method helloMethod;

    @BeforeEach
    public void init() throws NoSuchMethodException {
        helloMethod = MemberServiceImpl.class.getMethod("hello", String.class); //reflection으로 method 정보 추출
    }

    @Test
    void printMethod(){
        //execution 문법은 이 메서드 정보를 매칭해서 포인트컷 대상을 찾아낸다
        //public java.lang.String com.hello.aop.member.MemberServiceImpl.hello(java.lang.String)
        log.info("helloMethod ::: {}", helloMethod);
    }

    //생략 아예 안한 pointcut
    @Test
    void exactMatch(){
        pointcut.setExpression("execution(public String com.hello.aop.member.MemberServiceImpl.hello(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue(); //메타 정보 일치 여부 확인
    }


    //생략 많이 한 pointcut
    @Test
    void allMatch(){
        //(접근제어자) 반환타입, (선언타입) , 메서드 이름, 파라미터, (예외)
        // (..) 은 (0..*)과 같다
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatch(){
        pointcut.setExpression("execution(* hello(..))");;
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatchStar1(){
        pointcut.setExpression("execution(* hel*(..))");; //method명 패턴 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }


    @Test
    void nameMatchStar2(){
        pointcut.setExpression("execution(* *l*(..))");; //method명 패턴 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void nameMatchFalse(){
        pointcut.setExpression("execution(* no(..))");; //method명 패턴 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageExactMatch1(){

        pointcut.setExpression("execution(* com.hello.aop.member.MemberServiceImpl.hello(..))");; //method명 패턴 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactMatch2(){

        pointcut.setExpression("execution(* com.hello.aop.member.*.*(..))");; //method명 패턴 매칭
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageExactFalse(){

        pointcut.setExpression("execution(* com.hello.aop.*.*(..))"); //실패, 딱 com.hello.aop에 있어야 한다
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    @Test
    void packageMatchSubPackage1(){
        pointcut.setExpression("execution(* com.hello.aop..*.*(..))"); //하위 패키지 포함
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void packageMatchSubPackage2(){
        pointcut.setExpression("execution(* com.hello..*..*.*(..))"); //하위 패키지 포함
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchSuperType(){

        //부모 타입을 선언해도 자식 타입은 매칭이 된다
        pointcut.setExpression("execution(public String com.hello.aop.member.MemberService.*(..))"); //부모타입
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    @Test
    void typeMatchInternal() throws NoSuchMethodException {

        Method internalMethod = MemberServiceImpl.class.getMethod("internal", String.class);

        //부모타입에 있는 메소드만 매칭 된다
        pointcut.setExpression("execution(public String com.hello.aop.member.MemberService.*(..))"); //부모타입
        assertThat(pointcut.matches(internalMethod, MemberServiceImpl.class)).isFalse();
    }

    //String 타입의 파라미터 허용
    //(String)
    @Test
    void argsMatch(){
        pointcut.setExpression("execution(* *(String))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //파라미터가 없어야 함
    //()
    @Test
    void argsMatchNoArgs(){
        pointcut.setExpression("execution(* *())");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isFalse();
    }

    //정확히 하나의 파라미터 허용, 모든 타입 허용
    //(Xxx)
    @Test
    void argsMatchStar(){
        pointcut.setExpression("execution(* *(*))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //숫자와 무관하게 모든 파라미터, 모든 타입 허용
    @Test
    void argsMatchAll(){
        pointcut.setExpression("execution(* *(..))");
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

    //String 으로 시작
    //(String), (String, Xxx), (String, Xxx, Xxx)
    @Test
    void argsMatchComplex(){
        pointcut.setExpression("execution(* *(String, ..))");
        //pointcut.setExpression("execution(* *(String, *))"); //파라미터 갯수 2개
        assertThat(pointcut.matches(helloMethod, MemberServiceImpl.class)).isTrue();
    }

}
