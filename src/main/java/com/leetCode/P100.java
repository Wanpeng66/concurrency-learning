package com.leetCode;

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P100
 * @Description: 判断两个二叉树是否相同(结构相同，节点的值也相同)
 * @date 2020/3/31 14:56
 */
public class P100 {
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if(!check( p,q )){
            return false;
        }
        Queue<Pair<TreeNode,TreeNode>> queue = new LinkedList<>(  );
        queue.add( new Pair<>( p,q ) );
        while(!queue.isEmpty()){
            Pair<TreeNode, TreeNode> poll = queue.poll();
            TreeNode n1 = poll.getKey();
            TreeNode n2 = poll.getValue();
            if (!check( p, q )) return false;
            queue.offer( new Pair<>( p.left,q.left ) );
            queue.offer( new Pair<>( p.right,q.right ) );
        }
        return true;
    }

    private Boolean check( TreeNode p, TreeNode q ) {
        if(p==null&&q==null){
            return true;
        }else if(p==null||q==null){
            return false;
        }else if(p.val!=q.val){
            return false;
        }
        return true;
    }

    private boolean Method2( TreeNode p, TreeNode q ) {
        if(p==null&&q==null){
            return true;
        }else if(p==null||q==null){
            return false;
        }else if(p.val!=q.val){
            return false;
        }else{
            return isSameTree( p.left,q.left )&&isSameTree( p.right,q.right );
        }
    }

    private boolean Method1( TreeNode p, TreeNode q ) {
        List<Integer> l1 = new ArrayList<>(  );
        List<Integer> l2 = new ArrayList<>(  );
        Tree2Array(p,l1);
        Tree2Array(q,l2);
        if(l1.size()!=l2.size()){
            return false;
        }
        for (int i = 0; i < l1.size(); i++) {
            if(! l1.get( i ).equals( l2.get( i ) )){
                return false;
            }
        }
        return true;
    }

    private void Tree2Array( TreeNode p, List<Integer> list ) {
        if(p==null){
            list.add( -1 );
            return;
        }
        list.add( p.val );
        Tree2Array( p.left,list );
        Tree2Array( p.right,list );

    }
}
class TreeNode {
        int val;
        TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
}
