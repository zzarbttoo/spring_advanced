package com.hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
@Slf4j
class CallServiceV1Test {

    @Autowired CallServiceV1 callServiceV1;

    @Test
    void external(){
        //log.info("target ::: {}", callServiceV0.getClass());
        callServiceV1.external();
    }

    @Test
    void internal(){
        callServiceV1.internal();
    }


}