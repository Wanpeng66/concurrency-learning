package com.wp.ThreadLocal;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * @author: wp
 * @Title: TL
 * @Description: TODO
 * @date 2020/2/26 19:23
 */
@Slf4j
public class TL {


    public static void main( String[] args ) {
        AtomicInteger count = new AtomicInteger( 0 );
        ThreadLocal<Integer> threadLocal = ThreadLocal.withInitial( new Supplier<Integer>() {
            @Override
            public Integer get() {
                return count.getAndIncrement();
            }
        } );
        ExecutorService pool = Executors.newFixedThreadPool( 3 );
        for(int i=0;i<5;i++){
               pool.submit( new Runnable() {
                   @Override
                   public void run() {
                       try{
                           log.info( "{} 的count值是{}",Thread.currentThread().getName(),threadLocal.get() );
                       }finally{
                           //线程池中的线程的threadLocal在每次执行完任务后一定要清理
                           //否则下一次执行任务还会拿到上次的数据，不安全也容易内存泄漏
                           threadLocal.remove();
                       }

                   }
               } );
        }
        pool.shutdown();
    }
}
