package com.wp.threadPool;

import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.function.BiConsumer;

/**
 * @author: wp
 * @Title: ForkJoin
 * @Description: TODO
 * @date 2020/2/25 16:09
 */
public class ForkJoin {

    static class Fibonacci  extends RecursiveTask<Integer> {
        private int n ;

        public Fibonacci( int i ) {
            this.n = i;
        }

        @Override
        protected Integer compute() {
            if(n<=1)
                return n;
            Fibonacci f1 = new Fibonacci(n-1);
            f1.fork();
            Fibonacci f2 = new Fibonacci(n-2);
            //Integer compute = f2.compute();
            f2.fork();
            Integer join2 = f2.join();
            Integer join1 = f1.join();
            return join2+join1;
        }
    }

    static class MR extends RecursiveTask<Map<String,Integer>>{
        private String[] datas;
        private int start;
        private int end;
        public MR( String[] datas, int  start, int end ) {
            this.datas = datas;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Map<String, Integer> compute() {
            /**
            * 总结了一个范式
             * 第一部分写最小粒度时怎么做，比如就只有一个字符串时，开始统计字母
             * 第二部分，写怎么分治，怎么划分任务，一般就是两个ForkJoinTask对象，一个fork,一个compute
             * 第三部分，就是汇总工作，将分治后的结果拿到然后合并返回
            */
            //如果只有一个字符串后，开始统计
            if(end-start==1){                          // 第一部分
                return cal(datas[start]);
            }
            //如果有多个字符串，则二分法截取，分治
            int mid = (start+end)/2;                  //第二部分
            MR m1 = new MR( datas,start,mid );
            m1.fork();
            MR m2 = new MR( datas,mid,end );

            Map<String, Integer> compute = m2.compute();
            Map<String, Integer> join = m1.join();
            return merge(compute,join);                //第三部分
        }

        private Map<String, Integer> merge( Map<String, Integer> map1, Map<String, Integer> map2 ) {
            Map<String, Integer> results = new HashMap<>( 36 );
            results.putAll( map1 );
            map2.forEach( new BiConsumer<String, Integer>() {
                @Override
                public void accept( String key, Integer val ) {
                    if(results.containsKey( key )){
                        results.put( key,results.get( key )+val );
                    }else{
                        results.put( key,val );
                    }
                }
            } );
            return results;

        }

        private Map<String, Integer> cal( String data ) {
            Map<String, Integer> results = new HashMap<>( 36 );
            if(!StringUtils.isEmpty( data )){
                String[] strs = data.split( "\\s+" );
                for (String str : strs) {
                    Integer value = results.get( str );
                    if(null!=value){
                        results.put( str,value+1 );
                    }else{
                        results.put( str,1 );
                    }
                }
            }
            return results;
        }
    }
    public static void main( String[] args ) {
        ForkJoinPool pool = new ForkJoinPool( 4 );
        /*Fibonacci fi = new Fibonacci( 7 );
        Integer invoke = pool.invoke( fi );
        System.out.println(invoke);*/

        String[] datas = {"hello world","hello me","hello fork",
                "hello join","fork join in world"};
        MR mr = new MR( datas,0,datas.length );
        Map<String, Integer> invoke = pool.invoke( mr );
        invoke.forEach( new BiConsumer<String, Integer>() {
            @Override
            public void accept( String key, Integer val ) {
                System.out.println("key:"+key+",value:"+val);
            }
        } );

    }
}
