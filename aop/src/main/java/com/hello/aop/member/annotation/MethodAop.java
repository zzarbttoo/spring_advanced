package com.hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD) //Method에 붙이는 annotation
@Retention(RetentionPolicy.RUNTIME)
public @interface MethodAop {

    String value(); //annotation도 값을 가질 수 있다

}
