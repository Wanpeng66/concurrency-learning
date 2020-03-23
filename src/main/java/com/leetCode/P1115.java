package com.leetCode;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: wp
 * @Title: P1115
 * @Description: TODO 交替打印foobar
 * @date 2020/3/19 15:43
 */
public class P1115 {
    private volatile int n;
    private volatile boolean flag = true;
    CyclicBarrier cb = new CyclicBarrier( 2 );

    public P1115(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
                if(flag){
                    printFoo.run();
                    flag = false;
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            // printFoo.run() outputs "foo". Do not change or remove this line.

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
                if(!flag){
                    printBar.run();
                    flag = true;
                    try {
                        cb.await();
                    } catch (BrokenBarrierException e) {
                        e.printStackTrace();
                    }
                }
            // printBar.run() outputs "bar". Do not change or remove this line.

        }
    }
}

class P1115_2{
    private  int n;
    private Semaphore foo = new Semaphore( 1 );
    private Semaphore bar = new Semaphore( 0 );

    public P1115_2(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
                foo.acquire();
                printFoo.run();
                bar.release();
        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            bar.acquire();
            printBar.run();
            foo.release();
        }
    }
}

class P1115_3{
    private Lock lock = new ReentrantLock( true );
    Condition foo = lock.newCondition();
    Condition bar = lock.newCondition();
    private int n;
    private volatile boolean flag = true;

    public P1115_3(int n) {
        this.n = n;

    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            lock.lock();
            try{
                while(!flag){
                    foo.await();
                }
                printFoo.run();
                flag = false;
                bar.signalAll();
            }catch(Exception e){
                e.printStackTrace();
            }finally{
                lock.unlock();
            }
        }
        bar.signalAll();
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
                lock.lock();
                try{
                    while(flag){
                        bar.await();
                    }
                    printBar.run();
                    flag = true;
                    foo.signalAll();
                }catch(Exception e){
                    e.printStackTrace();
                }finally{
                    lock.unlock();
                }
        }
        foo.signalAll();
    }
}

class P1115_4{
    private int n;
    private AtomicBoolean flag = new AtomicBoolean( true );

    public P1115_4(int n) {
        this.n = n;
    }

    public void foo(Runnable printFoo) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while(!flag.get());
            printFoo.run();
            while(flag.compareAndSet( true,false ));

        }
    }

    public void bar(Runnable printBar) throws InterruptedException {

        for (int i = 0; i < n; i++) {
            while(flag.get());
            printBar.run();
            while(flag.compareAndSet( false,true ));

        }
    }
}
