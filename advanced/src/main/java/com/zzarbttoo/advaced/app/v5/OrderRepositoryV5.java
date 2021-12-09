package com.zzarbttoo.advaced.app.v5;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.callback.TraceTemplate;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.template.AbstractTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class OrderRepositoryV5 {

    private final TraceTemplate template;

    public OrderRepositoryV5(LogTrace trace) {
        this.template = new TraceTemplate(trace);
    }

    public void save(String itemId){

        template.execute("OrderRepository.save()", () -> {

            if (itemId.equals("ex")){
                throw new IllegalStateException("exception!");
            }

            //상품 저장에 1초 걸린다 가정
            sleep(1000);
            return null;
        });

    }

    private void sleep(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
