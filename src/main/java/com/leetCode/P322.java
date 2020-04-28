package com.leetCode;

import java.util.Arrays;

/**
 * @author: wp
 * @Title: P322
 * @Description: 322. 零钱兑换
 * @date 2020/4/26 21:46
 */
public class P322 {
    public int num = Integer.MAX_VALUE;
    public int coinChange(int[] coins, int amount) {
        Arrays.sort( coins );
        int max = amount/coins[0]+1;
        int t1 = 0;
        int t2 = 0;
        solveWithBackTracking(coins,amount,t1,t2,max);
        if(num==Integer.MAX_VALUE){
            return -1;
        }
        return num;
    }

    private void solveWithBackTracking( int[] coins, int amount, int t1, int t2, int max ) {
        if(t2>max){
            return ;
        }
        if(t1>amount){
            return ;
        }
        if(t1==amount){
            if(t2<num){
                num = t2;
            }
            return ;
        }
        for (int i = 0; i < coins.length; i++) {
            int val = coins[i];
            if(val<=amount && t1+val<=amount ){
                solveWithBackTracking( coins,amount,t1+val,t2+1,max );
            }

        }
    }

    public static void main( String[] args ) {
        P322 p = new P322();
        int i = p.coinChange( new int[]{5, 1, 2}, 50 );
        System.out.println(i);
    }
}
class P322_2{
    public int coinChange(int[] coins, int amount) {
        Arrays.sort( coins );
        int tmp = 0;
        int count = 0;
        int n = coins.length;
        for (int i = coins.length - 1; i >= 0; i--) {
            int coin = coins[i];
            while(tmp<=amount){
                tmp += coin;
                count++;
            }
            tmp -= coin;
            count--;
            if(tmp+coin == amount){
                return ++count;
            }
        }
        if(tmp==amount){
            return count;
        }else{
            return -1;
        }
    }
    public static void main( String[] args ) {
        P322_2 p = new P322_2();
        int i = p.coinChange( new int[]{186,419,83,408}, 6249 );
        System.out.println(i);
    }
}

class P322_3{
    public int coinChange(int[] coins, int amount) {
        if(amount==0){
            return 0;
        }
        if(amount<0){
            return -1;
        }
        Arrays.sort( coins );
        int[] res = new int[amount+1];
        Arrays.fill( res, Integer.MAX_VALUE);
        res[amount] = amount;
        int count = 1;
        for (int i = amount; i > 0; i--) {
            for (int coin : coins) {
                if(res[i]<Integer.MAX_VALUE){
                    int index = i - coin;
                    if(index>=0 && count<res[index]){
                        res[index] = count;
                    }
                }
            }
            count++;
        }
        if(res[0]==Integer.MAX_VALUE){
            return -1;
        }
        return res[0];


    }

    private int pd1( int[] coins, int amount ) {
        if(amount==0){
            return 0;
        }
        if(amount<0){
            return -1;
        }
        //先对硬币进行面值从小到大排序
        Arrays.sort( coins );
        //拿额定值除以面值最小的硬币，求出最多需要多少个硬币（相当于这个完全n叉树最高有多高）
        int max = amount/coins[0]+1;
        //最多硬币数量+额定值 一起确定二维数组的大小
        //二维数组的行可以理解为硬币个数，列可以理解为这么多硬币面值的总和
        boolean[][] res = new boolean[max][amount+1];
        //对00进行特殊处理
        res[0][0] = true;
        //开始填表
        //从1开始，i为硬币的个数，k为i个硬币的总面值。
        for (int i = 1; i < max; i++) {
            for (int coin : coins) {
                for (int k = 0; k <= amount-coin; k++) {
                    //如果为true，则说明上一次循环中，有总面值为k的情况，则这一次循环需要在k的基础上进行相加
                    if(res[i-1][k]){
                        //如果相加的值刚好为额定值，则返回k，即最少的硬币个数
                        if(k+coin==amount){
                            return i;
                        }else{
                            //如果值不等于额定值，则将对应的数组元素设置为true
                            res[i][k+coin] = true;
                        }

                    }
                }
            }
        }
        //如果循环结束还没有返回，则说明在给定的硬币面值里凑不成额定值
        return -1;
    }

    public static void main( String[] args ) {
        P322_3 p = new P322_3();

        int i = p.coinChange( new int[]{1,2,5}, 11 );
        System.out.println(i);
    }
}
