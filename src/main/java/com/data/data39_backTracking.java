package com.data;

import org.omg.CORBA.INTERNAL;

import java.util.Arrays;
import java.util.Stack;
import java.util.function.IntConsumer;

/**
 * @author: wp
 * @Title: data39_backTracking
 * @Description: 回溯算法
 * @date 2020/4/22 20:24
 */
public class data39_backTracking {
    static int max = Integer.MIN_VALUE;
    public static void pack(){

        int[] things = {10,20,30,5,3,6,7,50,9,100};
        f(0,0,things,10,100);
    }

    private static void f(  int cw, int index, int[] things, int size, int tw ) {
        //临界条件
        //cw >= tw 说明背包重量超过限制
        //index >= size 说明物品已遍历完
        if(cw>=tw || index >= size){
            //只有小于限制条件的重量才有效
            if(cw > max && cw<=tw){
                max = cw;
            }
            return;
        }
        //选择不放进背包
        f(cw,index+1,things,size,tw);
        //选择放进背包
        f(cw+things[index],index+1,things,size,tw);
    }

    public static void queens8(){
        int[] res = new int[8];
        execute(0,res);
        Arrays.stream( res ).forEach( value -> System.out.print(value+",") );
    }

    private static void execute( int i, int[] res ) {
        if(i==res.length){
            return;
        }
        for(int k=0;k<8;k++){
            if(isOk(i,k,res)){
                res[i] = k;
                break;
            }
        }
        execute( ++i,res );
    }

    private static boolean isOk( int r,int c, int[] res ) {
        int left = c-1;
        int right = c+1;
        for(int j=r-1;j>=0;j--){
            if(res[j] == c){
                return false;
            }
            if(left>=0 && res[r] == left){
                return false;
            }
            if(right<8 && res[r] == right){
                return false;
            }
            left--;
            right++;
        }
        return true;
    }

    public static void main( String[] args ) {
        pack();
        System.out.println(max);
        queens8();
    }
}
