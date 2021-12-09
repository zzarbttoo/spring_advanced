package com.zzarbttoo.advaced.app.v4;

import com.zzarbttoo.advaced.app.trace.TraceId;
import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV4 {

    private final LogTrace trace;

    public void save(String itemId){
        TraceStatus status = null;

        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                //상품 아이디가 ex일 경우 예외 발생
                if (itemId.equals("ex")){
                    throw new IllegalStateException("exception!");
                }

                //상품 저장에 1초 걸린다 가정
                sleep(1000);
                return null;
            }
        };

        template.execute("OrderRepository.save()");


    }

    private void sleep(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
