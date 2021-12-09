package com.zzarbttoo.advaced.app.trace.template;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;

public abstract class AbstractTemplate<T> {

    private final LogTrace trace;

    public AbstractTemplate(LogTrace trace){
        this.trace = trace;
    }

    //리턴 타입이 계속 달라지기 때문에 제너릭 이용
    public T execute(String message){
        TraceStatus status = null;

        try {

            status = trace.begin(message);

            //로직 호출(추상화)
            T result = call();

            trace.end(status);

            return result;

        }catch (Exception e){

            trace.exception(status, e);
            throw e;
        }
    }

    protected abstract T call();
}
