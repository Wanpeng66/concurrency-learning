package com.leetCode;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: wp
 * @Title: P1
 * @Description: 计算两数之和
 * @date 2020/3/27 20:13
 */
public class P1 {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer,Integer> map = new HashMap<>( nums.length );
        for (int i = 0; i < nums.length; i++) {
                map.put( nums[i],i );
        }
        for (int i = 0; i < nums.length; i++) {
            int v1 = nums[i];
            int v2 = target - v1;
            if(map.containsKey( v2 ) && map.get( v2 )!=i){
                return new int[]{i,map.get( v2 )};
            }
        }
        return null;
    }

    public static void main( String[] args ) {
        int[] nums = {3,3};
        System.out.println(twoSum( nums,6 ));
    }
}
