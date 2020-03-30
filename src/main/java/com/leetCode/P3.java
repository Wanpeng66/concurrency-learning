package com.leetCode;

import java.util.*;

/**
 * @author: wp
 * @Title: P3
 * @Description: 无重复字符的最长子串
 *   滑动窗口是数组/字符串问题中常用的抽象概念。
 *   窗口通常是在数组/字符串中由开始和结束索引定义的一系列元素的集合，
 *   即 [i, j)（左闭，右开）。而滑动窗口是可以将两个边界向某一方向“滑动”的窗口。
 *   例如，我们将 [i,j)向右滑动1个元素，则它将变为 [i+1, j+1)（左闭，右开）。
 * @date 2020/3/27 21:16
 */
public class P3 {
    public static int lengthOfLongestSubstring(String s) {
        int n = s.length();
        if(n==1){
            return 1;
        }
        Set<Character> set = new HashSet<>(  );
        int length = 0,start = 0,end = 0;
        //滑动窗口
        while(start<n&&end<n){
            if(!set.contains( s.charAt( end ) )){
                set.add( s.charAt( end ) );
                end++;
                length = length>(end - start)?length:end - start;
            }else{
                set.remove( s.charAt( start ) );
                start++;
            }
        }

        return length;
    }

    public static void main( String[] args ) {
        String str = "aab";
        System.out.println(lengthOfLongestSubstring( str ));
    }
}
