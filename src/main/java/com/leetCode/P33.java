package com.leetCode;

/**
 * @author: wp
 * @Title: P33
 * @Description: 搜索旋转排序数组
 * @date 2020/3/24 19:52
 */
public class P33 {

    public static int search(int[] nums, int target) {
        if(nums.length<=0){
            return -1;
        }
        int index = recursion_search(nums,0,nums.length-1,target);
        return index;
    }

    private static int recursion_search( int[] nums, int low, int high, int target ) {
        if(low>=high){
            if(nums[low]==target){
                return low;
            }else{
                return -1;
            }
        }
        int middle = low + ((high-low)>>1);
        int f1 = recursion_search( nums,low,middle,target );
        int f2 = recursion_search( nums,middle+1,high,target );
        if(f1!=-1){
            return f1;
        }
        if(f2 != -1){
            return f2;
        }
       return -1;
    }

    public static void main( String[] args ) {
        int[] nums = {7,0,1,2,4,5,6};
        System.out.println(search( nums,0 ));
    }
}
