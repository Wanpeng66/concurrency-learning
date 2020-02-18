package com.wp.lockUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author: wp
 * @Title: Cdl
 * @Description: TODO
 * @date 2020/2/17 14:57
 */
@Slf4j
public class Cdl {
    private Object p = null;
    private Object q = null;
    private int i = 0;
    private Executor pool = Executors.newFixedThreadPool( 2 );

    public void dz(){
        do{
            log.info( "{} 第{}次对账开始...",Thread.currentThread().getName(),i );
            CountDownLatch latch = new CountDownLatch( 2 );
            pool.execute( new Runnable() {
                @Override
                public void run() {
                    if(i!=10){
                        p = new Object();
                    }else{
                        p = null;
                    }
                    log.info( "{} 获得p的值",Thread.currentThread().getName() );
                    latch.countDown();
                }
            } );

            pool.execute( new Runnable() {
                @Override
                public void run() {
                    if(i!=10){
                        q = new Object();
                    }else{
                        q = null;
                    }
                    log.info( "{} 获得q的值",Thread.currentThread().getName() );
                    latch.countDown();
                }
            } );
            try {
                latch.await();
                log.info( "拿到p和q的值后，{}开始比较...",Thread.currentThread().getName() );
                check( p,q );
                save();
                log.info( "{} 对账完成 ",Thread.currentThread().getName() );
            } catch (InterruptedException e) {
                log.error( "发生异常...",e );
            }
            i++;
        }while(null!=p&&null!=q);

        log.info( "结束对账..." );
    }

    public void check(Object p,Object q){

    }

    public void save(){

    }

    public static void main( String[] args ) {
        Cdl cdl = new Cdl();
        cdl.dz();
    }

}
