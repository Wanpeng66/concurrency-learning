package com.data;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author: wp
 * @Title: P40_dynamic
 * @Description: 动态规划
 * @date 2020/4/23 21:53
 */
public class P40_dynamic {
    static int min = Integer.MAX_VALUE;
    public static int knapsack(int[] weight,int n,int w){
        //定义一个n行w+1列来记录所有的状态
        boolean[][] res = new boolean[n][w+1];
        //第一行特殊处理下
        res[0][0] = true;
        res[0][weight[0]] = true;
        //从第二行开始，都基于上一行的结果进行推算
        for (int i = 1; i < n; i++) {
            //不选择放入背包
            for (int j = 0; j <= w; j++){
                if(res[i-1][j]){
                    res[i][j] = true;
                }
            }
            //选择放入背包
            for(int j = 0; j <= w - weight[i]; j++){
                if(res[i-1][j]){
                    res[i][j+weight[i]] = true;
                }
            }
        }
        //推算完成后，直接拿最后一行倒序，找出最大的即可
        for (int i = w; i > 0; i--) {
            if(res[n-1][i]){
                return i;
            }
        }
        return 0;
    }

    public static void mixInt(int[][] nums,int n){
        backTracking(nums,n,0,0,0);
    }

    private static void backTracking( int[][] nums, int n,  int row, int col, int tmp ) {
        tmp+=nums[row][col];
        if(row==n-1&&col==n-1){
            if(tmp<min){
                min = tmp;
            }
            return;
        }
        if(row==n-1){
            //如果到达最低层，则只能往右边走
            backTracking( nums,n,row,col+1,tmp );
        }
        if(col==n-1){
            //如果到达最右侧，则只能往下走
            backTracking( nums,n,row+1,col,tmp );
        }
        if(col<n-1&&row<n-1){
            //往右走
            backTracking( nums,n,row,col+1,tmp );
            //往下走
            backTracking( nums,n,row+1,col,tmp );
        }

    }

    public static void dp(int[][] nums,int n){
        int[][] res = new int[n][n];
        int tmp = 0;
        for (int i = 0; i < n; i++) {
            tmp += nums[0][i];
            res[0][i] = tmp;
        }
        tmp = 0;
        for (int i = 0; i < n; i++) {
            tmp += nums[i][0];
            res[i][0] = tmp;
        }
        for(int k=1;k<n;k++){
            for(int i=1;i<n;i++){
                res[k][i] = nums[k][i]+Math.min( res[k-1][i],res[k][i-1] );
            }
        }
        min = res[n-1][n-1];

    }

    public static void coins( int[] money, int max, AtomicInteger num ){
        int t1 = 0;
        int t2 = 0;
        solveWithBackTracking(money,max,num,t1,t2);
    }

    private static void solveWithBackTracking( int[] money, int max, AtomicInteger num, int t1, int t2 ) {
        if(t2>=num.get()){
            return ;
        }
        if(t1>max){
            return ;
        }
        if(t1==max){
            if(t2<num.get()){
                num.set( t2 );
            }
            return ;
        }
        for (int i = 0; i < money.length; i++) {
            int val = money[i];
            solveWithBackTracking( money,max,num,t1+val,t2+1 );
        }
    }

    public static int coin2(int[] money,int max){
        int i = max / money[0] + 1;
        boolean[][] res = new boolean[i][max+1];
        res[0][0] = true;
        for (int k = 1; k < i; k++) {
            for (int val : money) {
                for (int j = 0; j < max + 1 - val; j++) {
                    if(res[k-1][j]){
                        res[k][j+val] = true;
                    }
                }
            }
        }
        for(int k=1;k<i;k++){
            if(res[k][max]){
                return k;
            }
        }
        return -1;
    }


    public static void main( String[] args ) {
        int[] things = {10,20,30,5,3,6,7,50,9,100};
        //int i = knapsack( things, 10, 100 );

        int[][] res = {{1,3,5,9},{2,1,3,4},{5,2,6,7},{6,8,4,3}};
        dp( res,4 );
        //System.out.println(min);
        int[] money = {1,2147483647};
        int max = 2;

        AtomicInteger num = new AtomicInteger( max/money[0] );
        coins( money,max,num );
        System.out.println(num.get());
        int i = coin2( money, 55 );
        //System.out.println(i);
    }
}
