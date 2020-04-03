package com.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: wp
 * @Title: P1046
 * @Description: 求最后一块石头的重量
 * @date 2020/4/3 12:44
 */
public class P1046 {

    public int lastStoneWeight(int[] stones) {
        if(stones.length==0){
            return 0;
        }
        if(stones.length==1){
            return stones[0];
        }
        if(stones.length==2){
            return Math.abs( stones[0]-stones[1] );
        }
        initHeap2(stones);
        while (!(stones[1]==0&&stones[2]==0)){
            int tmp = Math.max( stones[1],stones[2] );
            stones[0] -= tmp;
            if(stones[1]>stones[2]){
                stones[1] = 0;
            }else{
                stones[2] = 0;
            }
            initHeap2( stones );
        }
        return stones[0];

    }

    private void initHeap2( int[] stones ) {
        int size = stones.length;
        for(int i=size/2;i>=0;i--){
            int index = i;
            while(true){
                int maxPos = index;
                if(2*index+1<size && stones[2*index+1]>stones[index]){
                    maxPos = 2*index+1;
                }
                if(2*index+2<size && stones[2*index+2]>stones[maxPos]){
                    maxPos = 2*index+2;
                }
                if(maxPos==index){
                    break;
                }
                swap(stones,maxPos,index);
                index = maxPos;
            }
        }
    }
    private void swap( int[] array, int maxPos, int index ) {
        int tmp = array[maxPos];
        array[maxPos] = array[index];
        array[index] = tmp;
    }


    public static void main( String[] args ) {
        int[] array = {2,7,4,1,8,1};
        P1046 p = new P1046();
        int weight = p.lastStoneWeight( array );
        System.out.println(weight);
    }
}
