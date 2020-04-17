package com.leetCode;

/**
 * @author: wp
 * @Title: P122
 * @Description: 122. 买卖股票的最佳时机 II
 * @date 2020/4/15 20:32
 */
public class P122 {

    public int maxProfit(int[] prices) {
        int val = 0;
        for (int i = 0; i < prices.length-1; i++) {
            int diff = prices[i+1]-prices[i];
            if(diff>0){
                val += diff;
            }
        }
        return val;
    }

    public static void main( String[] args ) {
        int[] val = {6,1,3,2,4,7};
        P122 p = new P122();
        int i = p.maxProfit( val );
        System.out.println(i);
    }
}
