package com.zzarbttoo.advaced.app.v5;

import com.zzarbttoo.advaced.app.trace.callback.TraceCallback;
import com.zzarbttoo.advaced.app.trace.callback.TraceTemplate;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
동시에 여러번 호출하면 동시성 문제가 발생함
FieldLogTrace가 싱글톤이여서 하나밖에 없다
이 필드를 여러 스레드가 동시에 접근하기 때문에 문제가 발생한다
 */

@RestController
//@Controller + @ResponseBody
public class OrderControllerV5 {

    private final OrderServiceV5 orderService;
    private final TraceTemplate traceTemplate;
    private final LogTrace trace;

    //@Autowired
    public OrderControllerV5(OrderServiceV5 orderService, LogTrace trace) {
        this.orderService = orderService;
        this.trace = trace;
        this.traceTemplate = new TraceTemplate(trace);
    }

    @GetMapping("/v5/request")
    public String request(String itemId){
        return traceTemplate.execute("OrderController.request()", new TraceCallback<String>() {
            @Override
            public String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        });
    }

}
