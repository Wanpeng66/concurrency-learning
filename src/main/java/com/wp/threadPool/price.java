package com.wp.threadPool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: price
 * @Description: TODO
 * @date 2020/2/22 12:58
 */
@Slf4j
public class price {

    static class Task implements Callable<Boolean>{
        private String str ;

        public Task( String str ) {
            this.str = str;
        }

        @Override
        public Boolean call() throws Exception {
            log.info( "{}查询{}的报价...",Thread.currentThread().getName(),str );
            TimeUnit.SECONDS.sleep( 2 );
            log.info( "{}拿到{}的报价并保存",Thread.currentThread().getName(),str );
            return true;
        }
    }

    public static void main( String[] args ) {
        FutureTask<Boolean> a  = new FutureTask<>( new Task( "a" ) );
        FutureTask<Boolean> b  = new FutureTask<>( new Task( "b" ) );
        FutureTask<Boolean> c  = new FutureTask<>( new Task( "c" ) );


        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        pool.submit( a );
        pool.submit( b );
        pool.submit( c );


    }
}
