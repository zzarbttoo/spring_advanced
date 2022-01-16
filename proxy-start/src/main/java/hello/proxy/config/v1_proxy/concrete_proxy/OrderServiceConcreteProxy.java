package hello.proxy.config.v1_proxy.concrete_proxy;

import hello.proxy.app.v2.OrderRepositoryV2;
import hello.proxy.app.v2.OrderServiceV2;
import hello.proxy.trace.TraceStatus;
import hello.proxy.trace.logtrace.LogTrace;

public class OrderServiceConcreteProxy extends OrderServiceV2 {


    private final OrderServiceV2 target;
    private final LogTrace logTrace;

    public OrderServiceConcreteProxy(OrderServiceV2 target, LogTrace logTrace) {

        //부모 타입에 생성자가 있을 경우 자식타입에서 호출을 해줘야한다(java 문법상)
        //근데 지금 기본 생성자가 없는 상태
        super(null); //proxy 역할만 할 것이기 때문에 위에서 안받아옴
        this.target = target;
        this.logTrace = logTrace;
    }

    @Override
    public void orderItem(String itemId) {

        TraceStatus status = null;
        try {
            status = logTrace.begin("OrderService.orderItem ()");
            //target 호출
            target.orderItem(itemId);
            logTrace.end(status);

        } catch (Exception e) {
            logTrace.exception(status, e);
            throw e;
        }


    }
}
