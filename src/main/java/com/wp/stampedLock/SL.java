package com.wp.stampedLock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.StampedLock;

/**
 * @author: wp
 * @Title: SL
 * @Description: TODO
 * @date 2020/2/17 14:14
 */
@Slf4j
public class SL {
    private StampedLock lock = new StampedLock();
    private int x,y;

    /**
    * @Author wp
    * @Description  先通过乐观读去读取临界资源，然后在判断是否有写操作，
     * 如果有则升级为悲观读锁，重新读取，以确保读取到最新的值
    * @Date  2020/2/17 14:32
    * @Param  * @param
    * @return int
    */
    public int calculate(){
        long stamp = lock.tryOptimisticRead();
        int a = x;
        int b = y;
        if(!lock.validate( stamp )){
            stamp = lock.readLock();
            try{
                a = x;
                b = y;
            }finally{
                lock.unlockRead( stamp );
            }
        }
        return a+b;
    }

}
