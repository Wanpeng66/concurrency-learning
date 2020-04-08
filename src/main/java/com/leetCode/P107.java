package com.leetCode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P107
 * @Description: 107. 二叉树的层次遍历 II
 * @date 2020/4/8 21:09
 */
public class P107 {
    public List<List<Integer>> levelOrderBottom( TreeNode root) {
        List<List<Integer>> result = new LinkedList<>(  );
        Queue<TreeNode> queue = new LinkedList<>(  );
        int count = 1;
        queue.offer( root );
        while(!queue.isEmpty()){
            List<Integer> list = new LinkedList<>(  );
            int k = 0;
            for(int i=0;i<count;i++){
                TreeNode node = queue.poll();
                if(null!=node){
                    list.add( node.val );
                    if(null!=node.left){
                        queue.offer( node.left ) ;
                        k++;
                    }
                    if(null!=node.right){
                        queue.offer( node.right );
                        k++;
                    }
                }

            }
            if(!list.isEmpty()){
                result.add( list );
            }
            count = k;
        }
        Collections.reverse( result );
        return result;

    }
}
