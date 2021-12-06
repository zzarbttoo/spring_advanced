package com.zzarbttoo.advaced.app.v2;

import com.zzarbttoo.advaced.app.trace.TraceId;
import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV1;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {

    private final OrderRepositoryV2 orderRepositoryV2;
    private final HelloTraceV2 trace;

    public void orderItem(TraceId traceId, String itemId){

        TraceStatus status = null;

        try {
            status = trace.beginSync(traceId, "OrderService.orderItem()");
            orderRepositoryV2.save(status.getTraceId(), itemId);
            trace.end(status);
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야한다(application 흐름을 바꾸면 안된다)
        }
    }
}
