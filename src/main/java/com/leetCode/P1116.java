package com.leetCode;

import lombok.SneakyThrows;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.IntConsumer;

/**
 * @author: wp
 * @Title: P1116
 * @Description: 交叉打印零与奇偶数
 * @date 2020/3/18 20:54
 */
public class P1116 {
    ReentrantLock lock = new ReentrantLock( true );
    Condition zero = lock.newCondition();
    Condition odd = lock.newCondition();
    Condition even = lock.newCondition();
    private volatile int n ;
    private volatile int count = 1;
    private volatile int flag;
    public P1116(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public  void zero( IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try{
            while(count<=n){
                if(flag!=0){
                    zero.await();
                }else{
                    printNumber.accept( 0 );
                    if(count%2==0){
                        flag = 2;
                        even.signalAll();
                    }else{
                        flag = 1;
                        odd.signalAll();
                    }
                }

            }
            odd.signalAll();
            even.signalAll();
        }finally{
            lock.unlock();
        }
    }

    public  void even(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try{
            while(count<=n){
                if(flag !=2){
                    even.await();
                }else{
                    printNumber.accept( count++ );
                    flag = 0;
                    zero.signalAll();
                }
            }
            zero.signalAll();
            odd.signalAll();
        }finally{
            lock.unlock();
        }
    }

    public  void odd(IntConsumer printNumber) throws InterruptedException {
        lock.lock();
        try{
            while(count<=n){
                if(flag!=1){
                    odd.await();
                }else{
                    printNumber.accept( count++ );
                    flag = 0;
                    zero.signalAll();
                }
            }
            zero.signalAll();
            even.signalAll();
        }finally{
            lock.unlock();
        }
    }

    public static void main( String[] args ) {
        P1116 p = new P1116( 2 );
        ExecutorService pool = Executors.newFixedThreadPool( 3 );
        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.zero( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("零线程打印"+value);
                    }
                } );
            }
        } );
        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.odd( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("奇数线程打印"+value);
                    }
                } );
            }
        } );
        pool.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.even( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("偶数线程打印"+value);
                    }
                } );
            }
        } );
        pool.shutdown();
    }
}
class P1116_2{
    private volatile int n;
    Semaphore zero = new Semaphore( 1 );
    Semaphore odd = new Semaphore( 0 );
    Semaphore even = new Semaphore( 0 );
    private volatile int count = 1;
    private volatile  int flag = 0;
    public P1116_2(int n) {
        this.n = n;
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void zero(IntConsumer printNumber) throws InterruptedException {
        while(count<=n){
            if(flag==0){
                zero.acquire();
                if(count>n){
                    break;
                }
                printNumber.accept( 0 );
                if(count%2==0){
                    flag = 2;
                    even.release();
                }else{
                    flag = 1;
                    odd.release();
                }
            }

        }
        even.release();
        odd.release();
    }

    public void even(IntConsumer printNumber) throws InterruptedException {
        while(count<=n){
            even.acquire();
            if(count>n){
                break;
            }
            printNumber.accept( count++ );
            flag = 0;
            zero.release();
        }
        zero.release();
        odd.release();
    }

    public void odd(IntConsumer printNumber) throws InterruptedException {
        while(count<=n){
            odd.acquire();
            if(count>n){
                break;
            }
            printNumber.accept( count++ );
            flag = 0;
            zero.release();
        }
        zero.release();
        even.release();
    }

    public static void main( String[] args ) {
        P1116_2 p = new P1116_2( 2 );
        ExecutorService pool2 = Executors.newFixedThreadPool( 3 );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.zero( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("零线程打印"+value);
                    }
                } );
            }
        } );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.odd( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("奇数线程打印"+value);
                    }
                } );
            }
        } );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.even( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("偶数线程打印"+value);
                    }
                } );
            }
        } );
        pool2.shutdown();
    }
}

class P116_3{
    private volatile int n;
    private CyclicBarrier cb = new CyclicBarrier( 2 );
    private volatile int count = 1;
    private volatile CyclicBarrier odd;
    private volatile CyclicBarrier even;
    private volatile int flag = 0;

    public P116_3(int n) {
        this.n = n;
    }

    public void zero(IntConsumer printNumber)  {
        while(count<=n){
            if(flag==0){
                printNumber.accept( 0 );
                if(count%2==0){
                    flag = 2;
                    even = cb;
                }else{
                    flag = 1;
                    odd = cb;
                }
                try {
                    cb.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                even = null;
                odd = null;
            }
        }
    }

    public void even(IntConsumer printNumber)  {
        while(count<=n){
            if(null!=even&&flag==2){
                printNumber.accept( count++ );
                try {
                    even.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                even = null;
                flag = 0;
            }
        }
    }

    public void odd(IntConsumer printNumber)  {
        while(count<=n){
            if(null!=odd&&flag==1){
                printNumber.accept( count++ );
                try {
                    odd.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                odd = null;
                flag = 0;
            }
        }
    }

    public static void main( String[] args ) {
        P116_3 p = new P116_3( 10 );
        ExecutorService pool2 = Executors.newFixedThreadPool( 3 );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.zero( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("零线程打印"+value);
                    }
                } );
            }
        } );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.odd( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("奇数线程打印"+value);
                    }
                } );
            }
        } );
        pool2.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                p.even( new IntConsumer() {
                    @Override
                    public void accept( int value ) {
                        System.out.println("偶数线程打印"+value);
                    }
                } );
            }
        } );
        pool2.shutdown();
    }
}