package com.wp.readWriteLcok;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author: wp
 * @Title: Cache
 * @Description: TODO
 * @date 2020/2/17 10:54
 */

@Slf4j
public class Cache {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock( true );
    private Lock r = rwl.readLock();
    private Lock w = rwl.writeLock();
    private Map<String,String> value = new HashMap<>( 32 );


    public String get(String key){
        log.info( "{} 开始执行get操作",Thread.currentThread().getName() );
        String val = null;
        r.lock();
        log.info( "{} 拿到读锁",Thread.currentThread().getName() );
        try{
            val = value.get( key );
        }finally{
            r.unlock();
            log.info( "{} 释放读锁",Thread.currentThread().getName() );
        }
        if(null==val){
            log.info( "{}  缓存中没有,需要按需加载 ",Thread.currentThread().getName() );
            w.lock();
            log.info( "{} 拿到写锁",Thread.currentThread().getName() );
            try{
                val = value.get( key );
                if(null==val){
                    val = "从数据库懒加载...";
                    value.put( key,val );
                    log.info( "{} 查询到结果，塞进缓存",Thread.currentThread().getName() );
                }
            }finally{
                log.info( "{} 释放写锁",Thread.currentThread().getName() );
                w.unlock();
            }

        }
        log.info( "{} 返回值 {} ",Thread.currentThread().getName(),val );
        return val;
    }

    public boolean put(String key,String val){
        try{
            w.lock();
            value.put( key,val );
        }finally{
            w.unlock();
        }
        return true;
    }

    public static void main( String[] args ) {
        Cache cache = new Cache();
        ExecutorService pool = Executors.newFixedThreadPool( 3 );
        for(int i=0;i<3;i++){
            pool.submit( new Runnable() {
                @Override
                public void run() {
                    cache.get( "wp" );
                }
            } );
        }
        pool.shutdown();
    }
}
