package com.zzarbttoo.advaced.app.config;

import com.zzarbttoo.advaced.app.trace.logtrace.LogTrace;
import com.zzarbttoo.advaced.app.trace.logtrace.ThreadLocalLogTrace;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LogTraceConfig {

    //싱글톤으로 등록이 된다
    @Bean
    public LogTrace logTrace(){
        return new ThreadLocalLogTrace();
    }


}
