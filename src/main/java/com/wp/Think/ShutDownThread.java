package com.wp.Think;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: ShutDownThread
 * @Description: TODO
 * @date 2020/2/27 11:16
 */
@Slf4j
public class ShutDownThread {
    static class Proxy{
        //最好是定义自己的中断标记,因为如果使用第三方代码，
        // 他们不一定能正常使用thread自带的中断标记
        private static volatile boolean flag = false;
        private static boolean isStart = false;
        private static Thread worker;

        public static synchronized void start(){
            if(isStart){
                return ;
            }
            worker = new Thread( new Runnable() {
                @Override
                public void run() {
                    //没有被中断则继续执行
                    while (!flag){
                       log.info( "{}开始收集数据...",Thread.currentThread().getName() );
                        try {
                            TimeUnit.SECONDS.sleep( 2 );
                        } catch (InterruptedException e) {
                            //捕获异常后会重置线程的中断标记,需要重新中断
                            Thread.currentThread().interrupt();
                        }
                    }
                    log.info( "收集工作终止..." );
                    isStart = false;
                }
            },"worker" );
            worker.start();
        }

        public synchronized static void end(){
            if(null!=worker){
                flag = true;
                worker.interrupt();
            }
        }


    }

    public static void main( String[] args ) {
        ExecutorService pool = new ThreadPoolExecutor( 2, 4, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>( 8 ), new ThreadFactory() {
            @Override
            public Thread newThread( Runnable r ) {
                return new Thread( r,"proxy-"+r.hashCode() );
            }
        } );

        pool.submit( new Runnable() {
            @Override
            public void run() {
                Proxy.start();
            }
        } );

        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                TimeUnit.SECONDS.sleep( 10 );
                log.info( "{}终止proxy的收集工作...",Thread.currentThread().getName() );
                Proxy.end();
            }
        } );
        pool.shutdown();
    }
}
