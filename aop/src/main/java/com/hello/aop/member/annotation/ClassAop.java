package com.hello.aop.member.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE) //Class에 붙이는 annotation
@Retention(RetentionPolicy.RUNTIME) //application 실행 중에는 이 annotation이 살아있다
public @interface ClassAop {

}
