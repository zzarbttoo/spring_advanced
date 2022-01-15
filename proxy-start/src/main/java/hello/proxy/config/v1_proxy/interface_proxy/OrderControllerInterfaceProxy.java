package hello.proxy.config.v1_proxy.interface_proxy;

import hello.proxy.app.v1.OrderControllerV1;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderControllerInterfaceProxy implements OrderControllerV1 {

    private final OrderControllerV1 target;
    private final LogTrace logTrace;


    @Override
    public String request(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderController.request()");
            //target 호출
            String request = target.request(itemId);
            logTrace.end(status);
            return request;

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }

    }

    //보안상의 이슈가 있어서 로그를 찍지 않을 경우를 가정
    @Override
    public String noLog() {
        return target.noLog(); //아무 일도 안하고 위임만 함
    }
}


