package com.wp.Think;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author: wp
 * @Title: MyGuardedSuspension
 * @Description: Guarded Suspension。所谓 Guarded Suspension，直译过来就是“保护性地暂停”,
 *               本质上是一种等待唤醒机制的实现，只不过 Guarded Suspension 模式将其规范化了.
 *               但 Guarded Suspension 模式在解决实际问题的时候，往往还是需要扩展的，扩展的方式有很多，
 *               下面的类就直接对 GuardedObject 的功能进行了增强,实现异步转同步
 * @date 2020/2/26 21:07
 */
@Slf4j
public class MyGuardedSuspension<T> {
    private static Map<String,Object> guards = new ConcurrentHashMap(16);
    private Lock lock = new ReentrantLock( true );
    private Condition done = lock.newCondition();
    private T data;
    private Integer timeOut ;


    public MyGuardedSuspension( T data, Integer timeOut ) {
        this.data = data;
        this.timeOut = timeOut;
    }

    public static <T> MyGuardedSuspension getResp( String key,T t, int timeOut){
        MyGuardedSuspension<T> data = new MyGuardedSuspension<>( t,timeOut );
        guards.put( key,data );
        return data;
    }

    public static <T> void  done(String key,T t){
        if(guards.containsKey( key )){
            MyGuardedSuspension<T> guard = (MyGuardedSuspension) guards.remove( key );
            guard.onChange( t );
        }
    }


    public T get( Predicate predicate ){
        lock.lock();
        try{
            while(!predicate.test( data )){
                done.await();
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
        return this.data;
    }

    public void onChange(T t){
        lock.lock();
        try{
            data = t;
            done.signalAll();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }

    @Data
    static
    class Message{
        private String name;

    }


    public static void main( String[] args ) {
        /**
        *  发送请求到消息队列，服务端从消息队列拿到消息消费后返回响应，
         *  让发送请求的线程等待并拿到响应
        */
        ExecutorService pool = Executors.newFixedThreadPool( 2 );
        String id = UUID.randomUUID().toString();
        CompletableFuture<Void> f1 = CompletableFuture.runAsync( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Message message = new Message();
                log.info( "发送消息..." );
                TimeUnit.SECONDS.sleep( 1 );//模拟发送消息到消息队列中
                MyGuardedSuspension resp = MyGuardedSuspension.getResp( id, message, 5 );
                message = (Message) resp.get( new Predicate() {
                    @Override
                    public boolean test( Object o ) {
                        Message msg = (Message) o;
                        return ! StringUtils.isEmpty( msg.getName() );
                    }
                } );
                System.out.println(message.getName());

            }
        },pool );
        CompletableFuture<Void> f2 = CompletableFuture.runAsync( new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                Message message = new Message();
                message.setName( "wp" );
                TimeUnit.SECONDS.sleep( 3 );
                log.info( "拿到响应..." );
                MyGuardedSuspension.done( id, message );
                log.info( "调用完done方法..." );
            }
        },pool );
        f1.join();
        f2.join();

    }

}
