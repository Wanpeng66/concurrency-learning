package com.leetCode;

import lombok.SneakyThrows;

import java.util.concurrent.*;

/**
 * @author: wp
 * @Title: P1117
 * @Description: TODO H2O 生成
 * @date 2020/3/19 17:49
 */
public class P1117 {
    private Semaphore hydrogen ;
    private Semaphore oxygen ;
    private CyclicBarrier cb ;
    public P1117() {
        hydrogen = new Semaphore( 1 );
        oxygen = new Semaphore( 2 );
        cb = new CyclicBarrier( 3 );
    }

    //氧原子
    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogen.acquire();
        releaseHydrogen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        hydrogen.release();

    }

    //氢原子
    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygen.acquire();
        releaseOxygen.run();
        try {
            cb.await();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
        oxygen.release();

    }

    public static void main( String[] args ) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool( 10 );
        P1117 p = new P1117();
        String str = "HOH";
        for (char c : str.toCharArray()) {
            if(c=='H'){
                pool.submit( new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        p.oxygen( new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("H");
                            }
                        } );
                    }
                } );
            }else{
                pool.submit( new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        p.hydrogen( new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("O");
                            }
                        } );
                    }
                } );
            }
        }

        pool.shutdown();
    }
}

class P1117_2{
    Semaphore hydrogen = new Semaphore( 2 );
    Semaphore oxygen = new Semaphore( 2 );
    public P1117_2() {

    }

    public void hydrogen(Runnable releaseHydrogen) throws InterruptedException {
        hydrogen.acquire(2);
        releaseHydrogen.run();
        oxygen.release(2);
    }

    public void oxygen(Runnable releaseOxygen) throws InterruptedException {
        oxygen.acquire();
        releaseOxygen.run();
        hydrogen.release();
    }

    public static void main( String[] args ) {
        ExecutorService pool = Executors.newFixedThreadPool( 10 );
        P1117_2 p = new P1117_2();
        String str = "OOHHHH";
        for (char c : str.toCharArray()) {
            if(c=='H'){
                pool.submit( new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        p.oxygen( new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("H");
                            }
                        } );
                    }
                } );
            }else{
                pool.submit( new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        p.hydrogen( new Runnable() {
                            @Override
                            public void run() {
                                System.out.print("O");
                            }
                        } );
                    }
                } );
            }
        }

        pool.shutdown();
    }
}
