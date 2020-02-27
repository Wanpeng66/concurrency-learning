package com.wp.threadPool;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 * @author: wp
 * @Title: YB
 * @Description: TODO
 * @date 2020/2/24 11:48
 */
@Slf4j
public class YB {
    public static void main( String[] args ) throws Exception {
        test07();
    }

    private static void test01() {
        CompletableFuture<Void> future = CompletableFuture.runAsync( new Runnable() {
            @Override
            public void run() {
                System.out.println( "hello 异步编程..." );
            }
        } );

        CompletableFuture<Object> thenApply = future.thenApply( new Function<Void, Object>() {
            @Override
            public Object apply( Void aVoid ) {
                System.out.println( "第二步 串行关系..." );
                return "success";
            }
        } );

        System.out.println(thenApply.join());
    }

    //尝试串行化的三个方法以及异常捕捉方法
    private static void test02(){
        CompletableFuture<Void> future = CompletableFuture.supplyAsync( new Supplier<String>() {
            @Override
            public String get() {
                log.info( "第一步 输出字符..." );
                return "hello world";
            }
        } ).thenApplyAsync( new Function<String, Object>() {
            @Override
            public Object apply( String s ) {
                log.info( "第二步 增加新内容..." );

                return s + " wp ";
            }
        } ).thenAccept( new Consumer<Object>() {
            @Override
            public void accept( Object o ) {
                log.info( "第三步 转大写..." );
                log.info( o.toString().toUpperCase() );
            }
        } ).thenRun( new Runnable() {
            @Override
            public void run() {
                //int i = 1/0;
                log.info( "完结..." );
            }
        } );
        future.exceptionally( new Function<Throwable, Void>() {
            @Override
            public Void apply( Throwable throwable ) {
                log.error( "异常:",throwable );
                return null;
            }
        } ).join();
    }

    //尝试and聚合 烧水泡茶
    private static void test03(){
        CompletableFuture<Object> task1 = CompletableFuture.runAsync( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info( "洗茶壶..." );
                TimeUnit.SECONDS.sleep( 1 );
            }
        } ).thenRun( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                log.info( "烧水..." );
                TimeUnit.SECONDS.sleep( 5 );
            }
        } ).thenApply( new Function<Void, Object>() {
            @Override
            public Object apply( Void aVoid ) {
                return "task1的结果...";
            }
        } ).exceptionally( new Function<Throwable, Void>() {
            @Override
            public Void apply( Throwable throwable ) {
                log.error( "task1 抛出异常", throwable );
                return null;
            }
        } );

        CompletableFuture<Object> task2 = CompletableFuture.supplyAsync( new Supplier<String>() {
            @SneakyThrows
            @Override
            public String get() {
                log.info( "洗茶杯..." );
                TimeUnit.SECONDS.sleep( 1 );
                return "茶杯洗好了...";
            }
        } ).thenApply( new Function<String, Object>() {
            @SneakyThrows
            @Override
            public Object apply( String s ) {
                log.info( s );
                log.info( "拿茶叶..." );
                TimeUnit.SECONDS.sleep( 1 );
                return "大红袍";
            }
        } ).exceptionally( new Function<Throwable, Void>() {
            @Override
            public Void apply( Throwable throwable ) {
                log.error( "task2 抛出异常", throwable );
                return null;
            }
        } );

        //下面这种聚合方法，没有参数没有返回
        /*task1.runAfterBoth( task2, new Runnable() {
            @Override
            public void run() {
                log.info( "按理说可以泡茶了，但是这个方法拿不到之前任务的结果" );
            }
        } ).join();*/

        //下面这种聚合方法，有参数(能拿到task1，task2的返回结果)但是没返回
        /*task1.thenAcceptBoth( task2, new BiConsumer<Void, Object>() {
            @Override
            public void accept( Void aVoid, Object o ) {
                log.info( "茶叶是"+o.toString() );
                log.info( "泡茶了..." );
            }
        } ).join();*/

        //下面这种聚合方法，可以拿到两个task的返回当做参数，也有返回
        Object join = task1.thenCombine( task2, new BiFunction<Object, Object, Object>() {
            @Override
            public Object apply( Object i, Object o ) {
                log.info( "task1的结果是{},task2的结果是{}",i,o );
                return "泡茶完成...";
            }
        } ).join();
        System.out.println(join);
    }


    //尝试or聚合 一个请假，项目经理或组长任一人审批后都通过
    private static void test04(){
        AtomicInteger i = new AtomicInteger( 0 );
        CompletableFuture<String> task1 = CompletableFuture.supplyAsync( new Supplier<String>() {
            @Override
            public String get() {

                if(i.get()!=0){
                    log.info( "组长已经同意请假，经理不需要审批了..." );
                    return "组长已经同意请假，经理不需要审批了";
                }
                long start = System.currentTimeMillis();
                while(!i.compareAndSet( 0,1 )){
                    if(start-System.currentTimeMillis()>50000) {
                        log.info("经理审批超时...");
                        return "经理审批超时...";
                    }
                }
                log.info( "经理同意请假..." );
                return "经理同意请假...";
            }
        } );

        CompletableFuture<String> task2 = CompletableFuture.supplyAsync( new Supplier<String>() {
            @Override
            public String get() {
                if(i.get()!=0){
                    log.info( "经理已经同意请假，组长不需要审批了" );
                    return "经理已经同意请假，组长不需要审批了";
                }
                long start = System.currentTimeMillis();
                while(!i.compareAndSet( 0,1 )){
                    if(start-System.currentTimeMillis()>50000) {
                        log.info( "组长审批超时..." );
                        return "组长审批超时...";
                    }
                }
                log.info( "组长同意请假..." );
                return "组长同意请假...";
            }
        } );

        //下面的聚合方法，拿不到前面任务的结果,也没有返回
        /*task2.runAfterEither( task1, new Runnable() {
            @Override
            public void run() {
                log.info( "拿不到上面的结果，但是按理说可以请假被批准了..." );
            }
        } ).join();*/

        //这种聚合方法，可以拿到第一个执行完的任务的返回
        // （task1先跑完那就拿task1的结果，task2先跑完就拿task2的结果）作为参数，但是没返回
        /*task2.acceptEither( task1, new Consumer<String>() {
            @Override
            public void accept( String s ) {
                System.out.println(s);
                log.info( "拿不到上面的结果，但是按理说可以请假被批准了..." );
            }
        } ).join();*/

        //这种聚合方法，可以拿到第一个执行完的任务的返回
        // （task1先跑完那就拿task1的结果，task2先跑完就拿task2的结果）作为参数，然后也有返回
        Object join = task2.applyToEither( task1, new Function<String, Object>() {
            @Override
            public Object apply( String s ) {
                return "得到审批结果:" + s + ",请假通过";
            }
        } ).join();
        System.out.println(join);
    }

    //下面等效于对账中的cb
    private static void test05(){
        AtomicInteger count = new AtomicInteger( 0 );
        BlockingQueue<String> datas = new LinkedBlockingQueue<>( 16 );
        ExecutorService pool2 = Executors.newFixedThreadPool( 2 );
        ExecutorService pool3 = Executors.newFixedThreadPool( 1 );
        pool3.submit( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                while(true){
                    String take = datas.take();
                    log.info( "拿到队列中数据,data:{}",take );
                }
            }
        } );
            while(count.get()<2){
                CompletableFuture<Object> task1 = CompletableFuture.supplyAsync( new Supplier<Object>() {
                    @SneakyThrows
                    @Override
                    public Object get() {
                        //TimeUnit.SECONDS.sleep( 2 );
                        log.info( "准备数据1成功..." );
                        return "准备数据1成功...";
                    }
                },pool2).exceptionally( new Function<Throwable, Object>() {
                    @Override
                    public Object apply( Throwable e ) {
                        log.info( "准备数据1失败，抛出异常" );
                        return "准备数据1失败，抛出异常:"+e.getLocalizedMessage();
                    }
                } );
                CompletableFuture<Object> task2 = CompletableFuture.supplyAsync( new Supplier<Object>() {
                    @SneakyThrows
                    @Override
                    public Object get() {
                        //TimeUnit.SECONDS.sleep( 4 );
                        log.info( "准备数据2成功..." );
                        return "准备数据2成功...";
                    }
                },pool2 ).exceptionally( new Function<Throwable, Object>() {
                    @Override
                    public Object apply( Throwable e ) {
                        log.info( "准备数据2失败，抛出异常" );
                        return "准备数据2失败，抛出异常:"+e.getLocalizedMessage();
                    }
                } );
                CompletableFuture<Object> task3 = task1.thenCombine( task2, new BiFunction<Object, Object, Object>() {
                    @SneakyThrows
                    @Override
                    public Object apply( Object o, Object o2 ) {
                        //log.info( "task1结果：{},task2结果:{},对账成功...",o,o2 );
                        TimeUnit.SECONDS.sleep( 2 );
                        datas.put( count.get()+"" );
                        return "将结果放入队列中,对账次数是"+count.getAndIncrement();
                    }
                } ).exceptionally( new Function<Throwable, Object>() {
                    @Override
                    public Object apply( Throwable e ) {

                        return "将结果放入队列中失败，抛出异常:"+e.getLocalizedMessage();
                    }
                });
                task3.join();
            }


    }

    //下面写法等效于对账中countdownlatch的用法
    private static void test06(){
        AtomicInteger count = new AtomicInteger( 0 );
        ExecutorService pool = Executors.newFixedThreadPool( 5 );
        while(count.get()<2){
            CompletableFuture<Object> task1 = CompletableFuture.supplyAsync( new Supplier<Object>() {
                @SneakyThrows
                @Override
                public Object get() {
                    TimeUnit.SECONDS.sleep( 2 );
                    log.info( "准备数据1成功..." );
                    return "准备数据1成功...";
                }
            },pool).exceptionally( new Function<Throwable, Object>() {
                @Override
                public Object apply( Throwable e ) {
                    log.info( "准备数据1失败，抛出异常" );
                    return "准备数据1失败，抛出异常:"+e.getLocalizedMessage();
                }
            } );
            CompletableFuture<Object> task2 = CompletableFuture.supplyAsync( new Supplier<Object>() {
                @SneakyThrows
                @Override
                public Object get() {
                    TimeUnit.SECONDS.sleep( 4 );
                    log.info( "准备数据2成功..." );
                    return "准备数据2成功...";
                }
            } ).exceptionally( new Function<Throwable, Object>() {
                @Override
                public Object apply( Throwable e ) {
                    log.info( "准备数据2失败，抛出异常" );
                    return "准备数据2失败，抛出异常:"+e.getLocalizedMessage();
                }
            } );
            CompletableFuture<Object> task3 = task1.thenCombine( task2, new BiFunction<Object, Object, Object>() {
                @SneakyThrows
                @Override
                public Object apply( Object o, Object o2 ) {
                    log.info( "task1结果：{},task2结果:{},对账成功...",o,o2 );
                    TimeUnit.SECONDS.sleep( 2 );
                    return "对账成功,对账次数为："+count.getAndIncrement();
                }
            } ).exceptionally( new Function<Throwable, Object>() {
                @Override
                public Object apply( Throwable e ) {

                    return "对账失败，抛出异常:"+e.getLocalizedMessage();
                }
            });
            log.info( (String) task3.join() );
        }
    }

    //completionService的用法(批量提交异步任务，并且使任务结果有序化)
    private static void test07() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        BlockingQueue queue = new LinkedBlockingQueue( 16 );
        ExecutorCompletionService<param> ecs = new ExecutorCompletionService<>( pool,queue );
        for(int i=0;i<5;i++){
            param param = new param();
            int finalI = i;
            ecs.submit( new Runnable() {
                @SneakyThrows
                @Override
                public void run() {
                   // TimeUnit.SECONDS.sleep( new Random().nextInt( 5 ) );
                    param.setName( finalI +"" );
                }
            }, param );
        }
        while(true){
            YB.param p = ecs.take().get();
            System.out.println(p.getName());
        }
    }

    @Data
    static class param{
        private String name;

    }
}
