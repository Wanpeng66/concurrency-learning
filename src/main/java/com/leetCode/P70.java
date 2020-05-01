package com.leetCode;

import org.omg.CORBA.INTERNAL;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author: wp
 * @Title: P70
 * @Description: 70. 爬楼梯
 * @date 2020/5/1 22:02
 */
public class P70 {
    int max = Integer.MIN_VALUE;
    Map<Integer,Integer> res = new HashMap<>(  );
    public int climbStairs(int n) {
        if(n==1){
            return 1;
        }
        if(n==2){
            return 2;
        }
        if(null!=res.get( n )){
            return res.get( n );
        }
        int tmp = climbStairs(n-1)+climbStairs(n-2);
        res.put( n,tmp );
        return tmp;
    }

    public  int dp( int n ) {
        if(n<3){
            return n;
        }
        int[] res = new int[n+1];
        res[0] = 0;
        res[1] = 1;
        res[2] = 2;
        for (int i = 3; i <= n; i++) {
            res[i] = res[i-1]+res[i-2];
        }
        return res[n];

    }

    private void bt( int floor, int num, int n ) {
        if(num>=n){
            if(num==n){
                max++;
            }
            return ;
        }
        bt( floor+1,num+1,n );
        bt( floor+1,num+2,n );
    }

    public static void main( String[] args ) {
        P70 p = new P70();
        int i = p.dp( 3 );
        System.out.println(i);
    }
}
