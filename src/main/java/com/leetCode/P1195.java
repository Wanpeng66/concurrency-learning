package com.leetCode;

import java.util.concurrent.Semaphore;
import java.util.function.IntConsumer;

/**
 * @author: wp
 * @Title: P1195
 * @Description: TODO 1195. 交替打印字符串
 * @date 2020/3/19 20:36
 */
public class P1195 {

    private int n;
    private volatile int count = 1;
    Semaphore semaphore = new Semaphore( 1,true );

    public P1195(int n) {
        this.n = n;
    }

    // printFizz.run() outputs "fizz".
    public void fizz(Runnable printFizz) throws InterruptedException {
        while(count<=n){
            semaphore.acquire();
            if(count%3==0){
                if(count%5!=0){
                    if(count>n){
                        break;
                    }
                    printFizz.run();
                    count++;
                }
            }
            semaphore.release();
        }
        semaphore.release();
    }

    // printBuzz.run() outputs "buzz".
    public void buzz(Runnable printBuzz) throws InterruptedException {
        while(count<=n){
            semaphore.acquire();
            if(count%5==0){
                if(count%3!=0){
                    if(count>n){
                        break;
                    }
                    printBuzz.run();
                    count++;
                }
            }
            semaphore.release();
        }
        semaphore.release();
    }

    // printFizzBuzz.run() outputs "fizzbuzz".
    public void fizzbuzz(Runnable printFizzBuzz) throws InterruptedException {
        while(count<=n){
            semaphore.acquire();
            if(count%5==0&&count%3==0){
                if(count>n){
                    break;
                }
                printFizzBuzz.run();
                count++;
            }
            semaphore.release();
        }
        semaphore.release();
    }

    // printNumber.accept(x) outputs "x", where x is an integer.
    public void number( IntConsumer printNumber) throws InterruptedException {
        while(count<=n){
            semaphore.acquire();
            if(!(count%5==0||count%3==0)){
                if(count>n){
                    break;
                }
                printNumber.accept( count++ );
            }
            semaphore.release();
        }
        semaphore.release();

    }
}
