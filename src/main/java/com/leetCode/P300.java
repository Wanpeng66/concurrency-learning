package com.leetCode;

import com.google.common.collect.Maps;

import java.util.*;

/**
 * @author: wp
 * @Title: P300
 * @Description: 300. 最长上升子序列
 * @date 2020/4/29 20:43
 */
public class P300 {
    int max = 0;
    public int lengthOfLIS(int[] nums) {
        if(nums.length<2){
            return nums.length;
        }
        TreeSet<Integer> set = new TreeSet<>(  );
        for (int num : nums) {
            Integer val = set.ceiling( num );
            if(null!=val){
                set.remove( val );
            }
                set.add( num );
        }
        return set.size();

    }

    private int dp( int[] nums ) {
        if(nums.length<2){
            return nums.length;
        }
        int[] res = new int[nums.length];
        Arrays.fill( res,1 );
        for (int i = 1; i < nums.length; i++) {
            for (int k = i-1; k >= 0; k--) {
                if(nums[i]>nums[k]){
                    res[i] = Math.max( res[i],res[k]+1 );
                }
            }
        }
        int max = Integer.MIN_VALUE;
        for (int re : res) {
            max = Math.max( max,re );
        }
        return max;
    }

    private int errorMethod( int[] nums ) {
        if(nums.length==0){
            return 0;
        }
        TreeSet<String> set = new TreeSet<>( ( k1, k2 ) -> {
            String[] s1 = k1.split( "," );
            String[] s2 = k2.split( "," );
            if(Integer.parseInt( s1[0] )!=Integer.parseInt( s2[0] )){
                return Integer.parseInt( s1[0] )-Integer.parseInt( s2[0] );
            }else{
                return Integer.parseInt( s1[1] )-Integer.parseInt( s2[1] );
            }
        } );

        for (int i = 0; i < nums.length; i++) {
            set.add( nums[i]+","+i );
        }
        String[] split = set.pollFirst().split( "," );
        int count = 0;
        int val = Integer.parseInt( split[0] );
        int index = Integer.parseInt( split[1] );
        for (String str : set) {
            String[] s2 = str.split( "," );
            int v2 = Integer.parseInt( s2[0] );
            int i2 = Integer.parseInt( s2[1] );
            if(v2>val && i2>index){
                count++;
                val = v2;
                index = i2;
            }
            if(i2==nums.length-1){
                break;
            }
        }
        return ++count;
    }

    private int method1( int[] nums ) {
        if(nums.length==0){
            return max;
        }
        bt(nums,0,1,0);

        return max+1;
    }

    private void bt( int[] nums, int i, int j, int tmp) {
        if(i>=nums.length || j >=nums.length){
            if(tmp>max){
                max = tmp;
            }
            return;
        }
        if(nums[i]<nums[j]){
            bt( nums,j,j+1,tmp+1 );
        }
        bt( nums,i,j+1,tmp );
        bt( nums,j,j+1,0 );
    }

    public static void main( String[] args ) {
        P300 p = new P300();
        int i = p.lengthOfLIS( new int[]{ 1,3,6,7,9,4,10,5,6 } );
        System.out.println(i);
    }
}
