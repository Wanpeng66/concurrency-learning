package com.leetCode;

/**
 * @author: wp
 * @Title: P53
 * @Description: 53. 最大子序和
 * @date 2020/4/21 19:50
 */
public class P53 {
    public int maxSubArray(int[] nums) {
        int cur = nums[0];
        int total = nums[0];
        for (int i = 1; i < nums.length; i++) {
            cur = Math.max( nums[i] , cur+nums[i] );
            total = Math.max( cur , total );
        }
        return total;
    }

    public static void main( String[] args ) {
        int[] nums = {1,2,-1,-2,2,1,-2,1,4,-5,4};
        P53 p = new P53();
        int i = p.maxSubArray( nums );
        System.out.println(i);
    }
}
