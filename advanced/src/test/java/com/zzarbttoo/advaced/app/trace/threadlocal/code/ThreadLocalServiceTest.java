package com.zzarbttoo.advaced.app.trace.threadlocal.code;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ThreadLocalServiceTest {

    private ThreadLocalService service = new ThreadLocalService();

    @Test
    void field(){
        log.info("main start");

        //스레드가 두개 경합하는 상황 만든다
        Runnable userA = () -> {
            service.logic("userA");
        };

        Runnable userB = () -> {
          service.logic("userB");
        };

        Thread threadA = new Thread(userA);
        threadA.setName("thread-A");
        Thread threadB = new Thread(userB);
        threadB.setName("thread-B");


        //A 가 완전 끝나고 B를 실행하는 상황
        //동시성 문제가 발생하지 않는 코드
        threadA.start();
        //sleep(2000);
        sleep(100);
        threadB.start();

        //countDownLatch 사용해도 됨
        sleep(3000); //메인 쓰레드 종료 대기

    }

    private void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}


//
//        Runnable userA = new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }
