package com.leetCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author: wp
 * @Title: P144
 * @Description: 144. 二叉树的前序遍历
 * @date 2020/4/11 14:10
 */
public class P144 {

    public List<Integer> preorderTraversal( TreeNode root) {
        Stack<TreeNode> stack = new Stack<>();
        List<Integer> result = new LinkedList<>(  );
        TreeNode node = root;
        while(null!=node || !stack.isEmpty()){
            while(null!=node){
                result.add( node.val );
                stack.push( node );
                node = node.left;
            }
            TreeNode pop = stack.pop();
            node = pop.right;
        }
        return result;
    }

    private List<Integer> methodWithRecursion( TreeNode root ) {
        List<Integer> result = new LinkedList<>(  );
        if(null==root){
            return result;
        }
        result.add( root.val );
        dfs(root.left,result);
        dfs(root.right,result);
        return  result;
    }

    private void dfs( TreeNode node, List<Integer> result ) {
        if(null==node){
            return;
        }
        result.add( node.val );
        dfs(node.left,result);
        dfs(node.right,result);
    }
}
