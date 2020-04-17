package com.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: wp
 * @Title: P455
 * @Description: 455. 分发饼干
 * @date 2020/4/16 21:48
 */
public class P455 {
    public int findContentChildren(int[] g, int[] s) {
        List<Integer> collect = Arrays.stream( s ).sorted().
                boxed().collect( Collectors.toList() );
        Arrays.sort( g );
        int count = 0;
        for (int i = 0; i < g.length; i++) {
            boolean flag = false;
            Iterator<Integer> iterator = collect.iterator();
            while(iterator.hasNext()){
                Integer next = iterator.next();
                if(next>=g[i]){
                    flag = true;
                    count++;
                    iterator.remove();
                    break;
                }
            }
            if(!flag){
                break;
            }
        }
        return count;
    }

    public static void main( String[] args ) {
        int[] g = {1,2,3};
        int[] s = {1,1};
        P455 p = new P455();
        int count = p.findContentChildren( g, s );
        System.out.println(count);
    }
}
