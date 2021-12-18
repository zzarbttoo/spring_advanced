package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

//client, server은 변하지 않고 프록시가 중간에서 처리해야 하기 때문에 프록시는 객체와 동일한 interface 상속 받음
@Slf4j
public class CacheProxy implements Subject{

    //프록시 입장에서 실제로 호출해야 하는 객체
    private Subject target;
    private String cacheValue;

    //client -> proxy -> realSubject
    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {

        log.info("프록시 호출");

        if (cacheValue == null){
            cacheValue = target.operation();
        }

        return cacheValue;
    }
}
