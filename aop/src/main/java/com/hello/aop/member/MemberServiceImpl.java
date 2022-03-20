package com.hello.aop.member;

import com.hello.aop.member.annotation.ClassAop;
import com.hello.aop.member.annotation.MethodAop;
import org.springframework.stereotype.Component;

@ClassAop
@Component //spring bean 등록
public class MemberServiceImpl implements MemberService {

    @Override
    @MethodAop("test value") //annotation에도 값을 넣을 수 있다
    public String hello(String param) {
        return "ok";
    }

    public String internal(String param){
        return "ok";
    }
}
