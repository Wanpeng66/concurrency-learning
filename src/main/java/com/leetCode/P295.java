package com.leetCode;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author: wp
 * @Title: P295
 * @Description: 295. 数据流的中位数
 * @date 2020/4/3 21:03
 */
public class P295 {
    int size = 0;
    PriorityQueue<Integer> bigHeap = new PriorityQueue( Comparator.reverseOrder() );
    PriorityQueue<Integer> smallHeap = new PriorityQueue(  );
    public P295() {

    }

    public void addNum(int num) {
        size += 1;
        if(size==1){
            smallHeap.offer( num );
        }else{
            //总数为偶数时，往大顶堆加元素
            if(size%2==0){
                //先拿num与小堆中最小的元素peek比较，如果比peek大，则num入小堆，peek大堆
                //否则num直接入大堆
                Integer peek = smallHeap.peek();
                if(num>peek){
                    peek = smallHeap.poll();
                    smallHeap.offer( num );
                    bigHeap.offer( peek );
                }else{
                    bigHeap.offer( num );
                }
            }else{
             //如果总数为奇数时，要往小堆加元素
             //拿num与大堆中最大的元素peek比较，如果比peek大，则直接入小堆，
             //如果peek比较大，则peek入小堆
                Integer peek = bigHeap.peek();
                if(num>peek){
                    smallHeap.offer( num );
                }else{
                    peek = bigHeap.poll();
                    bigHeap.offer( num );
                    smallHeap.offer( peek );
                }

            }

        }

    }

    public double findMedian() {
        if(size==0){
            return 0.0;
        }
        if(size%2!=0){
            Integer peek = smallHeap.peek();
            return peek.doubleValue();
        }else{
            Integer big = bigHeap.peek();
            Integer small = smallHeap.peek();
            return (big.doubleValue()+small.doubleValue())/2;

        }
    }

    public static void main( String[] args ) {
        int[] array = {};
        P295 p = new P295();
        p.addNum( 1 );
        p.addNum( 2 );
        System.out.println(p.findMedian());
        p.addNum( 3 );
        System.out.println(p.findMedian());
    }
}
