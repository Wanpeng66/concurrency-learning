package com.wp.threadPool;

import org.springframework.util.ObjectUtils;

import java.sql.Time;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author: wp
 * @Title: HotWater
 * @Description: 试用futureTask实现泡茶烧水
 * @date 2020/2/20 21:12
 */
public class HotWater {
    //一个taska去执行洗水壶，烧水，泡茶的任务，并且泡茶之前需要等待其他任务拿来茶叶
    //一个taskb去执行洗茶壶，洗茶杯，拿茶叶的任务

    static class TaskA implements Callable<String>{
        FutureTask<String> taskb;
        public TaskA( FutureTask<String> taskb ) {
            this.taskb = taskb;
        }

        @Override
        public String call() throws Exception {
            System.out.println("洗水壶...");
            TimeUnit.SECONDS.sleep( 1 );
            System.out.println("烧水...");
            TimeUnit.SECONDS.sleep( 15 );
            String s = taskb.get();
            System.out.println("茶叶是"+s+"，开始泡茶...");
            return "泡茶完成。。。";
        }
    }

    static class TaskB implements  Callable<String>{

        @Override
        public String call() throws Exception {
            System.out.println("洗茶壶...");
            TimeUnit.SECONDS.sleep( 1 );
            System.out.println("洗茶杯...");
            TimeUnit.SECONDS.sleep( 2 );
            System.out.println("拿茶叶...");
            return "大红袍";
        }
    }

    public static void main( String[] args ) throws ExecutionException, InterruptedException {
        FutureTask<String> b = new FutureTask<>( new TaskB(  ) );
        FutureTask<String> a = new FutureTask<>( new TaskA( b ) );

        Thread x = new Thread( a );
        Thread y = new Thread( b );
        x.start();
        y.start();
        System.out.println(a.get());


    }

}
