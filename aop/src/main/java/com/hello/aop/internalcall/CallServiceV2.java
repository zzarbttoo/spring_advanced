package com.hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

//지연조회
@Slf4j
@Component
public class CallServiceV2 {

    //applicationContext는 기능이 너무 방대하므로 다른 것으로 대체
    //private final ApplicationContext applicationContext;

    //    public CallServiceV2(ApplicationContext applicationContext) {
//        this.applicationContext = applicationContext;
//    }


    private final ObjectProvider<CallServiceV2> callServiceProvider;

    public CallServiceV2(ObjectProvider<CallServiceV2> callServiceProvider) {
        this.callServiceProvider = callServiceProvider;
    }

    //등록 후 지연해서 bean을 꺼내온다
    public void external(){
        log.info("call external");
        //CallServiceV2 callServiceV2 = applicationContext.getBean(CallServiceV2.class);
        CallServiceV2 callServiceV2 = callServiceProvider.getObject();
        callServiceV2.internal();
    }



    public void internal(){
        log.info("call internal");
    }

}
