package com.zzarbttoo.advaced.app.v4;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
/*
동시에 여러번 호출하면 동시성 문제가 발생함
FieldLogTrace가 싱글톤이여서 하나밖에 없다
이 필드를 여러 스레드가 동시에 접근하기 때문에 문제가 발생한다
 */

@RestController
//@Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV4 {

    private final OrderServiceV4 orderService;
    private final LogTrace trace;

    @GetMapping("/v4/request")
    public String request(String itemId){

        AbstractTemplate<String> template = new AbstractTemplate<String>(trace) {
            @Override
            protected String call() {
                orderService.orderItem(itemId);
                return "ok";
            }
        };

        return template.execute("OrderController.request()");

    }

}
