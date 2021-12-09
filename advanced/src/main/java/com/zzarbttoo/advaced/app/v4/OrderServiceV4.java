package com.zzarbttoo.advaced.app.v4;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV4 {

    private final OrderRepositoryV4 orderRepositoryV4;
    private final LogTrace trace;

    public void orderItem(String itemId){

        AbstractTemplate<Void> template = new AbstractTemplate<Void>(trace) {
            @Override
            protected Void call() {
                orderRepositoryV4.save(itemId);
                return null;
            }
        };

        template.execute("OrderService.orderItem()");


    }
}
