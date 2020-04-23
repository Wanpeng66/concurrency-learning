package com.data;

/**
 * @author: wp
 * @Title: P40_dynamic
 * @Description: 动态规划
 * @date 2020/4/23 21:53
 */
public class P40_dynamic {

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

    public static void main( String[] args ) {
        int[] things = {10,20,30,5,3,6,7,50,9,100};
        int i = knapsack( things, 10, 100 );
        System.out.println(i);
    }
}
