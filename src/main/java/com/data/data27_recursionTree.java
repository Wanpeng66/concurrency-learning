package com.data;

/**
 * @author: wp
 * @Title: data27_recursionTree
 * @Description: 用递归树来求解递归算法的时间复杂度
 * @date 2020/4/1 21:07
 */
public class data27_recursionTree {

    /**
    * 打印给定数组中全部数据的排列组合
    */
    public static void printPermutations( int[] data, int n, int k ) {
        if (k == 1) {
            for (int i = 0; i < n; ++i) {
                System.out.print(data[i] + " ");
            }    System.out.println();
        }
        for (int i = 0; i < k; ++i) {
            int tmp = data[i];
            data[i] = data[k-1];
            data[k-1] = tmp;
            printPermutations(data, n, k - 1);
            tmp = data[i];
            data[i] = data[k-1];
            data[k-1] = tmp;
        }
    }

    public static int cellsNumber(int n){
        if(n==0){
            return 1;
        }
        if(n<3){
            return 2 << (n-1);
        }
        if(n==3){
            return 7;
        }
        return (cellsNumber( n-1 )<<1) - cellsNumber( n-3 ) + cellsNumber( n-4 );
    }

    public static void main( String[] args ) {
        int[]a ={1, 2, 3, 4};
        //printPermutations(a, 4, 4);
        System.out.println(cellsNumber( 7 ));
    }
}
