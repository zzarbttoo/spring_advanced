package com.hello.aop.proxyvs.code;

import com.hello.aop.member.MemberService;
import com.hello.aop.member.MemberServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

/*
* 의존 관계 주입은 웬만하면 인터페이스 기반으로 주입받도록 하자
* */

@Slf4j
//@SpringBootTest(properties = {"spring.aop.proxy-target-class=false"}) //jdk 동적 프록시
@SpringBootTest(properties = {"spring.aop.proxy-target-class=true"}) //CGLIB
@Import(ProxyDIAspect.class)
public class ProxyDITest {

    @Autowired
    MemberService memberService; //interface 주입

    @Autowired
    MemberServiceImpl memberServiceImpl; //구체클래스 주입

    @Test
    void go(){
        log.info("memberService class ::: {}", memberService.getClass());
        log.info("memberServiceImpl class ::: {}", memberServiceImpl.getClass());
        memberServiceImpl.hello("hello");

    }

}
