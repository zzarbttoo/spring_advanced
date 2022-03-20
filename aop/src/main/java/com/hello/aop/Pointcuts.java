package com.hello.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    //@Pointcut 안에 들어가는 포인트컷 지시자(PCD)
    //execution : 메소드 실행 조인 포인트 매칭
    @Pointcut("execution(* com.hello.aop.order..*(..))")
    public void allOrder(){} //pointcut signature(메소드명 + 파라미터), 반환 타입 void

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){
    }

}
