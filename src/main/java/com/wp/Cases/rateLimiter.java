package com.wp.Cases;

import com.google.common.util.concurrent.RateLimiter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author: wp
 * @Title: RateLimiter
 * @Description: guava的限流器
 * @date 2020/2/28 20:06
 */
@Slf4j
public class rateLimiter {
    public static void main( String[] args ) {
        ThreadLocal<DateTimeFormatter> tl = ThreadLocal.withInitial( new Supplier<DateTimeFormatter>() {
            @Override
            public DateTimeFormatter get() {
                return DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm:ss" );
            }
        } );
        RateLimiter limiter = RateLimiter.create( 1 );
        ExecutorService pool = new ThreadPoolExecutor( 5,10,10, TimeUnit.SECONDS,new LinkedBlockingQueue<>( 32 ) );
        for(int i=0;i<5;i++){
            pool.submit( new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                    log.info( "{}开始获取令牌，时间:{}",
                            Thread.currentThread().getName(), LocalDateTime.now().format( tl.get() ) );
                    limiter.acquire();
                    log.info( "{}获取令牌，时间:{}",
                            Thread.currentThread().getName(), LocalDateTime.now().format( tl.get() ) );
                    TimeUnit.SECONDS.sleep( 10 );

                }
            } );
        }

        pool.shutdown();
    }
}
