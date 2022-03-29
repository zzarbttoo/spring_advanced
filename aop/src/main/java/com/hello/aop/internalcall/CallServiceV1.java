package com.hello.aop.internalcall;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CallServiceV1 {

    private CallServiceV1 callServiceV1;

    //생성자 주입을 할 경우 문제 발생
    //따라서 setter을 이용해서 주입
    //spring 2.6 이후는 순환 참조 금지이기 때문에 application.properties에 허용하도록 서렂ㅇ
    @Autowired
    public void setCallServiceV1(CallServiceV1 callServiceV1){
        log.info("callServiceV1 setter ::: {}", callServiceV1.getClass());
        this.callServiceV1 = callServiceV1;
    }

    public void external(){
        log.info("call external");
        callServiceV1.internal(); //외부
    }



    public void internal(){
        log.info("call internal");
    }

}
