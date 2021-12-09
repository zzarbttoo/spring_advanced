package com.zzarbttoo.advaced.app.trace.strategy;

import com.zzarbttoo.advaced.app.trace.strategy.code.strategy.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ContextV2Test {

    // 전략 패턴 적용
    // context 실행 시 전략을 인수로 전달
    @Test
    void strategyV1(){

        //ctrl + alt + V
        ContextV2 context = new ContextV2();
        context.execute(new StrategyLogic1());
        context.execute(new StrategyLogic2());
    }

    //전략 패턴 익명 내부 클래스
    @Test
    void strategyV2(){

        //ctrl + alt + V
        ContextV2 context = new ContextV2();

        context.execute(new Strategy() {
            @Override
            public void call() {
                log.info("비즈니스 로직 1 실행");
            }
        });
        context.execute(() -> log.info("비즈니스 로직 2 실행"));


    }

}
