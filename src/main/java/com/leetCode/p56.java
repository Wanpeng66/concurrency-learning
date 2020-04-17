package com.leetCode;

import java.util.*;
import java.util.function.IntFunction;
import java.util.function.Predicate;

/**
 * @author: wp
 * @Title: p56
 * @Description: 56. 合并区间 给出一个区间的集合，请合并所有重叠的区间。
 * @date 2020/4/16 19:52
 */
public class p56 {
    public int[][] merge(int[][] intervals) {
        intervals = Arrays.stream( intervals ).
                sorted( ( arr1, arr2 ) -> arr1[0] - arr2[0] ).
                toArray( int[][] :: new );
        List<Integer> index = new LinkedList<>(  );
        for (int i = 0; i < intervals.length-1; i++) {
            int[] arr1 = intervals[i];
            int[] arr2 = intervals[i+1];
            if(!(arr1[0]>arr2[1]||arr1[1]<arr2[0])){
                if(arr1[0]<arr2[0]){
                    arr2[0] = arr1[0];
                }
                if(arr1[1]>arr2[1]){
                    arr2[1] = arr1[1];
                }
                index.add( i );
            }
        }
        for (int i = 0; i < intervals.length; i++) {
            if(index.contains( i )){
                intervals[i] = null;
            }
        }
        intervals = Arrays.stream( intervals ).
                filter( ints -> null!=ints ).
                toArray( int[][] :: new  );
        return intervals;
    }

    public static void main( String[] args ) {
        int[][] array = {{2,3},{4,5},{6,7},{8,9},{1,10}};
        p56 p = new p56();
        int[][] merge = p.merge( array );
    }
}
