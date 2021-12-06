package com.zzarbttoo.advaced.app.v0;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV0 {

    public void save(String itemId){

        //상품 아이디가 ex일 경우 예외 발생
        if (itemId.equals("ex")){
            throw new IllegalStateException("exception!");
        }

        //상품 저장에 1초 걸린다 가정
        sleep(1000);
    }

    private void sleep(int millis) {

        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
