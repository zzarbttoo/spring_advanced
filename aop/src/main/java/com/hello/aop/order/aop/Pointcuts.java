package com.hello.aop.order.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.hello.aop.order..*(..))")
    public void allOrder(){} //pointcut signature(메소드명 + 파라미터), 반환 타입 void

    @Pointcut("execution(* *..*Service.*(..))")
    public void allService(){}

    //allOrder && allService
    @Pointcut("allOrder() && allService()")
    public void orderAndService(){
    }

}
