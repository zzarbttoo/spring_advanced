package com.hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(CallLogAspect.class)
@SpringBootTest
@Slf4j
class CallServiceV2Test {

    @Autowired CallServiceV2 callServiceV2;

    @Test
    void external(){
        //log.info("target ::: {}", callServiceV0.getClass());
        callServiceV2.external();
    }

    @Test
    void internal(){
        callServiceV2.internal();
    }


}