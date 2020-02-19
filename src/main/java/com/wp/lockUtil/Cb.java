package com.wp.lockUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: Cb
 * @Description: TODO
 * @date 2020/2/18 9:48
 */
@Slf4j
public class Cb {
    private Queue<Object[]> infos = new ArrayBlockingQueue<>( 16 );
    private ExecutorService pool = Executors.newFixedThreadPool( 2 );
    private ExecutorService main = Executors.newFixedThreadPool( 1 );


    public void check(Object p,Object q){
        try {
            TimeUnit.SECONDS.sleep( 2 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void save(){

    }

    public void dz(){
        int count = 0;
        int index = 0;
        CyclicBarrier barrier = new CyclicBarrier( 2, new Runnable() {
            @Override
            public void run() {
                //log.info( "{} 执行回调函数...此时infos size ={}",Thread.currentThread().getName(),infos.size() );
                main.submit( new Runnable() {
                    @Override
                    public void run() {
                        //log.info( "{} 执行聚合任务，此时infos size ={}"
                               // ,Thread.currentThread().getName(),infos.size() );
                        if(!infos.isEmpty()){
                            Object[] peek = infos.poll();
                            check( peek[0],peek[1] );
                            save();
                            log.info( "{}+{}", peek[0],peek[1]);
                        }
                    }
                } );
            }
        } );
        do{

            Object[] info = new Object[2];
            CountDownLatch latch = new CountDownLatch( 2 );
            int finalIndex = index;
            pool.submit( new Runnable() {
                @Override
                public void run() {
                    info[0] = "第"+ finalIndex;
                    try {
                        latch.countDown();
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            } );

            pool.submit( new Runnable() {
                @Override
                public void run() {
                    info[1] = finalIndex+"次";
                    try {
                        latch.countDown();
                        barrier.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            } );

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            infos.offer( info );
            //log.info( "第{}次对账数据准备完成...",count );
            count++;
            index++;
        }while(count<10);

        //log.info( "infos size = {} ",infos.size() );
    }

    public static void main( String[] args ) {
        Cb cb = new Cb();
        cb.dz();
    }


}
