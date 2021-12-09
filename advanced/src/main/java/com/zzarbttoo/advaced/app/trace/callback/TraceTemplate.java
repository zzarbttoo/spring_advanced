package com.zzarbttoo.advaced.app.trace.callback;

import com.zzarbttoo.advaced.app.trace.TraceStatus;
import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;

public class TraceTemplate {

    private final LogTrace trace;

    public TraceTemplate(LogTrace trace){
        this.trace = trace;
    }

    public <T> T execute(String message, TraceCallback<T> callback){

            TraceStatus status = null;

        try {

            status = trace.begin(message);

            //로직 호출(추상화)
            T result = callback.call();

            trace.end(status);

            return result;

        }catch (Exception e){

            trace.exception(status, e);
            throw e;
        }
    }

}
