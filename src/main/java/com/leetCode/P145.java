package com.leetCode;

import java.util.*;

/**
 * @author: wp
 * @Title: P145
 * @Description: 145. 二叉树的后序遍历
 * @date 2020/4/11 14:32
 */
public class P145 {
    public List<Integer> postorderTraversal( TreeNode root) {
        List<Integer> result = new LinkedList<>(  );
        Set<TreeNode> nodeSet = new HashSet<>(  );
        Stack<TreeNode> stack = new Stack<>();
        TreeNode node = root;
        while(node!=null || !stack.isEmpty()){
            while(node!=null){
                stack.push( node );
                node = node.left;
            }
            TreeNode pop = stack.pop();
            if(nodeSet.contains( pop )){
                result.add( pop.val);
                node = null;
            }else{
                stack.push( pop );
                nodeSet.add( pop );
                node = pop.right;
            }
        }
        return result;
    }

    private List<Integer> methodWithRecursion( TreeNode root ) {
        List<Integer> result = new LinkedList<>(  );
        if(null==root){
            return result;
        }
        dfs(root.left,result);
        dfs(root.right,result);
        result.add( root.val );
        return result;
    }

    private void dfs( TreeNode node, List<Integer> result ) {
        if(null==node){
            return;
        }
        dfs(node.left,result);
        dfs(node.right,result);
        result.add( node.val );
    }
}
