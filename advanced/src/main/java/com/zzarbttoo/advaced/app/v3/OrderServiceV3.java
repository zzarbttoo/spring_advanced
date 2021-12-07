package com.zzarbttoo.advaced.app.v3;

import com.zzarbttoo.advaced.app.trace.TraceId;
import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV2;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV3 {

    private final OrderRepositoryV3 orderRepositoryV3;
    private final LogTrace trace;

    public void orderItem(String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderService.orderItem()");
            orderRepositoryV3.save(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야한다(application 흐름을 바꾸면 안된다)
        }
    }
}
