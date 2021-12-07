package com.zzarbttoo.advaced.app.v3;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.hellotrace.HelloTraceV2;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
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
public class OrderControllerV3 {

    private final OrderServiceV3 orderService;
    private final LogTrace trace;

    @GetMapping("/v3/request")
    public String request(String itemId){

        TraceStatus status = null;

        try {
            status = trace.begin("OrderController.request()");
            orderService.orderItem(itemId);
            trace.end(status);
            return "ok";
        }catch (Exception e){
            trace.exception(status, e);
            throw e; //예외를 꼭 다시 던져줘야한다(application 흐름을 바꾸면 안된다)
        }
    }

}
