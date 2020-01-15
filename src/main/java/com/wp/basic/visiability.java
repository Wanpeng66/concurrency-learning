package com.wp.basic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wp
 * @Title: visiability
 * @Description: 测试可见性
 * @date 2020/1/14 17:17
 */
public class visiability {
    private Long count = 0L;

    public static void main( String[] args ) throws Exception {
        CountDownLatch latch = new CountDownLatch( 2 );
        visiability target = new visiability();
        ExecutorService executorService = Executors.newFixedThreadPool( 2 );
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<1000;i++){
                    target.count++;
                }
                latch.countDown();
            }
        };
        executorService.submit( runnable );
        executorService.submit( runnable );
        latch.await();
        System.out.println(target.count);
        executorService.shutdown();
    }
}
