package com.leetCode;

import java.util.*;

/**
 * @author: wp
 * @Title: P108
 * @Description: 有序数组转为二叉搜索树
 * @date 2020/4/1 14:06
 */
public class P108 {

    public TreeNode sortedArrayToBST(int[] nums) {
        int start = 0;
        int end  = nums.length-1;

        return Helper(nums,start,end);
    }

    private TreeNode Helper( int[] nums, int start, int end ) {
        if(start>end){
            return null;
        }
        int middle = start  + ((end-start)>>1);
        TreeNode node = new TreeNode( nums[middle] );
        node.left = Helper( nums,start,middle-1 );
        node.right = Helper( nums,middle+1,end );
        return node;
    }

    private TreeNode RecursionMethod( int[] nums ) {
        int size = nums.length;
        if(size==0){
            return null;
        }
        int middle = size >> 1;
        TreeNode root = new TreeNode( nums[middle] );
        int[] left = Arrays.copyOfRange( nums,0,middle );
        int[] right = Arrays.copyOfRange( nums, middle+1, size );
        creatTree(left,root);
        creatTree(right,root);
        return root;
    }

    private void creatTree( int[] array, TreeNode root ) {
        int size = array.length;
        if(size==0){
            return ;
        }
        int middle = size >> 1;
        TreeNode node = new TreeNode( array[middle] );
        if(array[middle]<root.val){
            root.left = node;
        }else{
            root.right = node;
        }
        int[] left = Arrays.copyOfRange( array,0,middle );
        int[] right = Arrays.copyOfRange( array, middle+1, size );
        creatTree(left,node);
        creatTree(right,node);
    }

    public static void main( String[] args ) {
        P108 p = new P108();
        int[] nums = {-10,-3,0,5,9};
        TreeNode treeNode = p.sortedArrayToBST( nums );

    }
}
