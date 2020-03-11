package com.data;

import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: data10_recursion
 * @Description: 递归
 *  一个问题只要同时满足以下3个条件，就可以用递归来解决：
 *  1.问题的解可以分解为几个子问题的解。何为子问题？就是数据规模更小的问题
 *  2.问题与子问题，除了数据规模不同，求解思路完全一样
 *  3.存在递归终止条件
 *
 *  写递归代码的关键就是找到如何将大问题分解为小问题的规律，并且基于此写出递推公式，
 *  然后再推敲终止条件，最后将递推公式和终止条件翻译成代码。
 * @date 2020/3/10 21:18
 */
public class data10_recursion {
    private static ConcurrentHashMap<Long,Long> cache = new ConcurrentHashMap<>( 100 );

    public static void main( String[] args ) throws Exception {
        //假如这里有n个台阶，每次你可以跨1个台阶或者2个台阶，请问走这n个台阶有多少种走法？
        forkJoinMethod();
        //Long f = f( 50 );
        //System.out.println(f);
    }

    //用迭代来解决
    private static Long f(int n){
        if(n==1){
            return 1L;
        }
        if(n==2){
            return 2L;
        }

        return f(n-1)+f(n-2);

    }

    //用fork/join来解决爬楼梯问题
    private static void forkJoinMethod() throws InterruptedException, ExecutionException {
        ForkJoinPool pool = new ForkJoinPool( 20 );
        ForkJoinTask<Integer> task = pool.submit( new MyTask( 50 ) );
        System.out.println(task.get());
    }

    static class MyTask extends RecursiveTask<Integer> {
        private int x;

        public MyTask( int x ) {
            this.x = x;
        }

        @Override
        protected Integer compute() {
            if(x == 1 || x == 2){
                return x;
            }
            /*Long tmp = cache.get( x );
            if(null!=tmp){
                return tmp.intValue();
            }*/
            MyTask task1 = new MyTask(x-1);
            MyTask task2 = new MyTask(x-2);
            invokeAll( task1,task2 );
            int total = task1.join()+task2.join();
            //cache.put( Long.parseLong( x+"" ),Long.parseLong( total+"" ) );
            return total;
        }
    }
}
