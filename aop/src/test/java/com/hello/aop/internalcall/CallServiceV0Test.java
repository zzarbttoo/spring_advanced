package com.hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
@Slf4j
class CallServiceV0Test {

    @Autowired CallServiceV0 callServiceV0;

     /*
     this.internal(내부호출)이기 때문에
     프록시를 거치지 않는다 -> 어드바이스를 적용할 수 없다

     aspectj를 직접 쓰면 상관 없는데 spring aop는 proxy 방식을 사용해서
     위와 같은 문제 발생
     */
    @Test
    void external(){
        //log.info("target ::: {}", callServiceV0.getClass());
        callServiceV0.external();
    }

    @Test
    void internal(){
        callServiceV0.internal();
    }


}