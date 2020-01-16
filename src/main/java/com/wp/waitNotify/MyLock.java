package com.wp.waitNotify;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: wp
 * @Title: MyLock
 * @Description: TODO
 * @date 2020/1/16 21:57
 */
public class MyLock {
    private static volatile List<Account> locks = new ArrayList<>(  );
    public static MyLock getInstance(){
        return Lock.lock;
    }

    public  boolean lock(Account a,Account b)  {
        try{
            synchronized (this){
                while(locks.contains( a )||locks.contains( b )){
                    this.wait();
                }
                locks.add( a );
                locks.add( b );
            }
        }catch(Exception e){
            unlock(a,b);
        }finally{
            return false;
        }
    }

    public void unlock( Account a, Account b ) {
        if(locks.contains( a )) locks.remove( a );
        if(locks.contains( b )) locks.remove( b );
        this.notifyAll();
        System.out.println( Thread.currentThread().getName()+"执行完转账，释放锁资源...");
    }

    public static class Lock{
        private static MyLock lock = new MyLock();
    }
}
