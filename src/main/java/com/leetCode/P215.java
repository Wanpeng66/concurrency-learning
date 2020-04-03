package com.leetCode;

import java.util.PriorityQueue;

/**
 * @author: wp
 * @Title: P215
 * @Description: 215. 数组中的第K个最大元素
 * @date 2020/4/3 16:14
 */
public class P215 {
    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> smallHeap = new PriorityQueue( k );
        for(int i=0;i<k;i++){
            smallHeap.offer( nums[i] );
        }
        for(int i=k;i<nums.length;i++){
            Integer peek = smallHeap.peek();
            if(nums[i]>peek){
                smallHeap.poll();
                smallHeap.offer( nums[i] );
            }
        }
        return smallHeap.peek();
    }
}
