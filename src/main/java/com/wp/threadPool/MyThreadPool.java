package com.wp.threadPool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author: wp
 * @Title: MyThreadPool.
 * @Description: 最简单的线程池实现
 * @date 2020/2/20 20:09
 */
public class MyThreadPool {
    //工作线程组
    private List<Thread> worker;
    //任务队列
    private BlockingQueue<Runnable> workQueue;

    public MyThreadPool( int size ) {
        workQueue = new ArrayBlockingQueue<>( 16 );
        worker = new ArrayList<>(  );
        for(int i=0;i<size;i++){
            WorkThread workThread = new WorkThread("MyThreadPool-"+i);
            workThread.start();
            worker.add( workThread );
        }

    }
    public void submit(Runnable command){
        try {
            workQueue.put( command );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    class WorkThread extends Thread{
        public WorkThread( String name ) {
            super( name );
        }
        @Override
        public void run(){
            while(true){
                try {
                    Runnable task = workQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void main( String[] args ) {
        MyThreadPool pool = new MyThreadPool( 2 );
        for(int i=0;i<5;i++){
         pool.submit( new Runnable() {
             @Override
             public void run() {
                 System.out.println(Thread.currentThread().getName()+"在执行任务...");
             }
         } );
        }
    }


}
