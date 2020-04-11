package com.leetCode;

import java.util.Stack;

/**
 * @author: wp
 * @Title: P98
 * @Description: 98. 验证二叉搜索树
 * @date 2020/4/10 21:55
 */
public class P98 {
    //搜索二叉树中序遍历时是一个有序的集合
    public boolean isValidBST(TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        double lower = - Double.MAX_VALUE;
        TreeNode node = root;
        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                stack.push( node );
                node = node.left;
            }
            TreeNode pop = stack.pop();
            if(pop.val<=lower ){
                return false;
            }else{
                lower = pop.val;
            }
            node = pop.right;
        }
        return true;
    }
    //下面方法不对
    private boolean dfs( TreeNode root,TreeNode head ) {
        if(null==root){
            return true;
        }
        boolean f1 = true;
        boolean f2 = true;
        if(null!=root.left){
            if(root.left.val>=root.val ||
                    (root.val>head.val && root.left.val<=head.val) ){
                f1 =  false;
            }else{
                f1 =  dfs(root.left,root);
            }
        }
        if(null!=root.right){
            if(root.right.val<=root.val ||
                    ( root.val<head.val && root.right.val >= head.val)){
                f2 =  false;
            }else{
                f2 = dfs(root.right,root);
            }
        }
        return !(!f1||!f2);
    }

    public static void main( String[] args ) {
        TreeNode node = P111.stringToTreeNode( "[3,null,30,10,null,null,15,null,45]" );
        P98 p = new P98();
        boolean validBST = p.isValidBST( node );
        System.out.println(validBST);
    }


}
