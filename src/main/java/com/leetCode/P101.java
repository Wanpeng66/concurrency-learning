package com.leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P101
 * @Description: 101. 对称二叉树
 * @date 2020/4/7 21:48
 */
public class P101 {

    public boolean isSymmetric(TreeNode root) {
        if(null==root){
            return true;
        }
        return dfs(root.left,root.right);
    }

    private boolean dfs( TreeNode node1, TreeNode node2 ) {
        if(null==node1&&null==node2){
            return true;
        }
        if(null==node1 || null ==node2){
            return false;
        }
        if(node1.val!=node2.val){
            return false;
        }
        return dfs(node1.left,node2.right)&&dfs( node1.right,node2.left );
    }

    private boolean bfs( TreeNode root ) {
        Queue<TreeNode> nodes = new LinkedList<>(  );
        nodes.offer( root.left );
        nodes.offer(root.right);
        while(!nodes.isEmpty()){
            TreeNode node1 = nodes.poll();
            TreeNode node2 = nodes.poll();
            if(null==node1&&null==node2){
                continue;
            }
            if(null==node1 || null==node2){
                return false;
            }
            if(node1.val!=node2.val){
                return false;
            }
            nodes.offer( node1.left );
            nodes.offer( node2.right );
            nodes.offer( node1.right );
            nodes.offer( node2.left );
        }
        return false;
    }
}
