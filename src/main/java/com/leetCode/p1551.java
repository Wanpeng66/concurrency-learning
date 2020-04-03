package com.leetCode;

import sun.applet.Main;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author: wp
 * @Title: p1551
 * @Description: 面试题40. 最小的k个数
 * @date 2020/4/3 15:13
 */
public class p1551 {
    public int[] getLeastNumbers(int[] arr, int k) {
        if(k==0){
            return new int[0];
        }
        PriorityQueue<Integer> bigHeap = new PriorityQueue<Integer>( k, ( o1, o2 ) -> {
            if(o1<o2) {
                return 1;
            }
            return -1;
        } );

        for(int i=0;i<k;i++){
            bigHeap.offer( arr[i] );
        }
        for(int i=k;i<arr.length;i++){
            Integer peek = bigHeap.peek();
            if(arr[i]<peek){
                bigHeap.poll();
                bigHeap.offer( arr[i] );
            }
        }
        int[] nums = new int[k];
        int index = 0;
        Iterator<Integer> iterator = bigHeap.iterator();
        while(iterator.hasNext()){
            nums[index] = iterator.next();
            index++;
        }
        return nums;
    }
    public static void main(String[] args){
        int[] array = {0,0,0,2,0,5};
        int k = 0;
        p1551 p = new p1551();
        p.getLeastNumbers( array,k );
    }
}
