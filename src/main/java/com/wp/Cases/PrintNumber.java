package com.wp.Cases;

import lombok.SneakyThrows;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: wp
 * @Title: PrintNumber
 * @Description: 两个线程，交替打印出奇数和偶数
 * @date 2020/2/29 18:59
 */
public class PrintNumber {
   static volatile int index;

    public static void main( String[] args ) {
        Method3();
    }

    //方法三最简单，通过共享标志来实现
    private static void Method3(){
        final Boolean[] flag = {true};
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        pool.submit( new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(index>100)
                        break;
                    if(flag[0]){
                        System.out.println("偶数线程打印:"+index);
                        index++;
                        flag[0] = false;
                    }

                }
            }
        } );
        pool.submit( new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(index>100)
                        break;
                    if(!flag[0]){
                        System.out.println("奇数线程打印:"+index);
                        index++;
                        flag[0] = true;
                    }

                }
            }
        } );

        pool.shutdown();

    }

    //第二种方法，是将每一个数都看成一个周期
    //在周期内，判断奇偶 然后相对应的线程打印，通过cb相互等待，
    //再通过cb的回调函数，将count+1，再次进入下一个周期
    private static void Method2(){
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        AtomicInteger count = new AtomicInteger( 0 );
        CyclicBarrier cb = new CyclicBarrier( 2, new Runnable() {
            @Override
            public void run() {
                //完成一个周期后，将count+1,进行下一个周期
                count.getAndIncrement();
            }
        } );
        pool.submit( new Runnable() {
            @Override
            public void run() {
                while(count.get()<=100){
                    if(count.get()%2!=0){
                        System.out.println("奇数线程打印:"+count.get());
                    }
                    try {
                        cb.await();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        } );
        pool.submit( new Runnable() {
            @Override
            public void run() {
                    while(count.get()<=100){
                        if(count.get()%2==0){
                            System.out.println("偶数线程打印:"+count.get());
                        }
                        try {
                            cb.await();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        } );

        pool.shutdown();
    }

    //第一种方法: 通过等待、通知模式，让两个线程相互等待通知，交替打印
    private static void Method1() {
        AtomicInteger count = new AtomicInteger( 0 );
        ReentrantLock lock  = new ReentrantLock(  );
        //奇数的条件变量
        Condition js = lock.newCondition();
        //偶数的条件变量
        Condition os = lock.newCondition();
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while(count.get()<=100){
                    lock.lock();
                    while(count.get()%2==0){
                        js.await();
                    }
                    System.out.println("奇数线程打印:"+count.getAndIncrement());
                    os.signalAll();
                    lock.unlock();
                }
            }
        } );
        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while(count.get()<=100){
                    lock.lock();
                    while(count.get()%2!=0){
                        os.await();
                    }
                    System.out.println("偶数线程打印:"+count.getAndIncrement());
                    js.signalAll();
                    lock.unlock();
                }

            }
        } );

        pool.shutdown();
    }
}
