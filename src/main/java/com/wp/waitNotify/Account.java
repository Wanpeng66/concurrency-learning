package com.wp.waitNotify;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author: wp
 * @Title: Account
 * @Description: TODO
 * @date 2020/1/16 21:54
 */
@Data
@Slf4j
public class Account {
    private volatile Integer balance;

    public void transfer(int num,Account target){
        MyLock instance = MyLock.getInstance();
        try {
            instance.lock(this,target);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.balance -= num;
        Integer targetBalance = target.getBalance();
        targetBalance +=num;
        target.setBalance( targetBalance );
        instance.unlock( this,target );
    }

    public static void main( String[] args ) throws InterruptedException {
        long begin = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newScheduledThreadPool(50);
        int k = 49;
        CountDownLatch latch = new CountDownLatch( k );
        Account a = new Account();
        a.setBalance( 1000 );
        Account b = new Account();
        b.setBalance( 1000 );
        for(int i=0;i<k;i++){
            threadPool.submit( new Runnable() {
                @Override
                public void run() {
                    a.transfer( 1,b);
                    latch.countDown();
                }
            } );
        }
        latch.await();
        long end = System.currentTimeMillis();
        log.info( "{} 个线程跑完，花了 {} 的时间......",k,end-begin );
        log.info( a.getBalance()+"" );
        threadPool.shutdown();

    }

}
