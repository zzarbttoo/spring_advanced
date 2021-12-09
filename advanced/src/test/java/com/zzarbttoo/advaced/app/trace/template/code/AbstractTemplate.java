package com.zzarbttoo.advaced.app.trace.template.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTemplate {

    public void execute(){
        long startTime = System.currentTimeMillis();
        //비즈니스 로직 실행
        call(); //상속
        //비즈니스 로직 종료
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;

        log.info("resultTime = {} ", resultTime);
    }


    //Template pattern
    //변하는 것은 자식 class로 해결
    protected abstract void call();

}
