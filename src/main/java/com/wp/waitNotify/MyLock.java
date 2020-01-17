package com.wp.waitNotify;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wp
 * @Title: MyLock
 * @Description: TODO
 * @date 2020/1/16 21:57
 */
@Slf4j
public class MyLock {
    private static volatile List<Account> locks = new ArrayList<>(  );
    public static MyLock getInstance(){
        return Lock.lock;
    }

    public synchronized  boolean lock(Account a,Account b) throws Exception {
                while(locks.contains( a )||locks.contains( b )){
                    this.wait();
                }
                locks.add( a );
                locks.add( b );
                return true;
    }

    public synchronized void unlock( Account a, Account b ) {
        if(locks.contains( a )) locks.remove( a );
        if(locks.contains( b )) locks.remove( b );
        this.notifyAll();
        log.info( "{} 执行完转账，释放锁资源...",Thread.currentThread().getName() );
    }

    public static class Lock{
        private static MyLock lock = new MyLock();
    }
}
