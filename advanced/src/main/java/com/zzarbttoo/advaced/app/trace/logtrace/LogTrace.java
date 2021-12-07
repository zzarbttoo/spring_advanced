package com.zzarbttoo.advaced.app.trace.logtrace;

import com.zzarbttoo.advaced.app.trace.TraceStatus;

public interface LogTrace {

    TraceStatus begin(String message);

    void end(TraceStatus status);
    void exception(TraceStatus status, Exception e);

}
