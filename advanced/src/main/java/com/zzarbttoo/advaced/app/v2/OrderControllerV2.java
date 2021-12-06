package com.zzarbttoo.advaced.app.v2;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV1;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@Controller + @ResponseBody
@RequiredArgsConstructor
public class OrderControllerV2 {

    private final OrderServiceV2 orderService;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(status.getTraceId(), itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야한다(application 흐름을 바꾸면 안된다)
        }
    }

}
