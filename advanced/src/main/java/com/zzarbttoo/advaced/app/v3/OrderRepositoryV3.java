package com.zzarbttoo.advaced.app.v3;

import com.zzarbttoo.advaced.app.trace.TraceId;
import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV2;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV3 {

    private final LogTrace trace;

    public void save(TraceId traceId, String itemId){
        TraceStatus status = null;

        try {
            status = trace.begin("OrderRepository.save()");

            //상품 아이디가 ex일 경우 예외 발생
            if (itemId.equals("ex")){
                throw new IllegalStateException("exception!");
            }

            //상품 저장에 1초 걸린다 가정
            sleep(1000);
            trace.end(status);

        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야한다(application 흐름을 바꾸면 안된다)
        }

    }

    private void sleep(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
