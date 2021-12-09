package com.zzarbttoo.advaced.app.v5;

import com.zzarbttoo.advaced.app.trace.callback.TraceCallback;
import com.zzarbttoo.advaced.app.trace.callback.TraceTemplate;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceV5 {

    private final OrderRepositoryV5 orderRepositoryV5;
    private final TraceTemplate template;

    //@Autowired
    public OrderServiceV5(OrderRepositoryV5 orderRepositoryV5, LogTrace trace) {
        this.orderRepositoryV5 = orderRepositoryV5;
        this.template = new TraceTemplate(trace);
    }

    public void orderItem(String itemId){

        template.execute("OrderService.orderItem()", () -> {
            orderRepositoryV5.save(itemId);
            return null;
        });


    }
}
