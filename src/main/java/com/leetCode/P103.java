package com.leetCode;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P103
 * @Description: 103. 二叉树的锯齿形层次遍历
 * @date 2020/4/8 20:38
 */
public class P103 {
    public List<List<Integer>> zigzagLevelOrder( TreeNode root) {
        List<List<Integer>> result = new LinkedList<>(  );
        Queue<TreeNode> queue = new LinkedList<>(  );
        queue.offer( root );
        int floor = 1;
        int k = 1;
        while(!queue.isEmpty()){
            List<Integer> list = new LinkedList<>(  );
            int count = 0;
            for(int i=0;i<k;i++){
                TreeNode node = queue.poll();
                if(node!=null){
                    list.add( node.val );
                    if(null!=node.left){
                        queue.offer( node.left );
                        count++;
                    }
                    if(null!=node.right){
                        queue.offer( node.right );
                        count++;
                    }
                }
            }
            if(!list.isEmpty()){
                result.add( list );
            }
            k = count;
            floor++;
        }
        for (int i = 1; i < result.size(); i+=2) {
            List<Integer> list = result.get( i );
            Collections.reverse( list );
        }
        return result;
    }

    private List<List<Integer>> bfsMethod1( TreeNode root ) {
        List<List<Integer>> result = new LinkedList<>(  );
        Queue<TreeNode> queue = new LinkedList<>(  );
        queue.offer( root );
        int floor = 1;
        int k = 1;
        while(!queue.isEmpty()){
            List<Integer> list = new LinkedList<>(  );
            int count = 0;
            for(int i=0;i<k;i++){
                TreeNode node = queue.poll();
                if(node!=null){
                    list.add( node.val );
                    if(null!=node.left){
                        queue.offer( node.left );
                        count++;
                    }
                    if(null!=node.right){
                        queue.offer( node.right );
                        count++;
                    }
                }
            }
            if(!list.isEmpty()){
                if(floor%2!=0){
                    result.add( list );
                }else{
                    Collections.reverse( list );
                    result.add( list );
                }
            }

            k = count;
            floor++;
        }
        return result;
    }
}
