package com.leetCode;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P102
 * @Description: 102. 二叉树的层序遍历
 * @date 2020/4/8 18:35
 */
public class P102 {

    public List<List<Integer>> levelOrder( TreeNode root) {
        List<List<Integer>> result = new LinkedList<>(  );
        int floor = 0;
        dfs(root,floor,result);
        return result;
    }

    private void dfs( TreeNode root, int floor, List<List<Integer>> result ) {
        if(null==root){
            return;
        }
        if(result.size()==floor){
            result.add( new LinkedList<Integer>(  ) );
        }
        List<Integer> list = result.get( floor );
        list.add( root.val );
        floor++;
        dfs(root.left,floor,result);
        dfs(root.right,floor,result);
    }

    private List<List<Integer>> bfs( TreeNode root ) {
        List<List<Integer>> result = new LinkedList<>(  );
        Queue<TreeNode> queue = new LinkedList<>(  );
        queue.offer( root );
        int count = 1;
        while(!queue.isEmpty()){
            int js = 0;
            List<Integer> floor = new LinkedList<>(  );
            for(int i=0;i<count;i++){
                TreeNode node = queue.poll();
                if(null!=node){
                    floor.add( node.val );
                    if(null!=node.left){
                        queue.offer( node.left );
                        js++;
                    }
                    if(null!=node.right){
                        queue.offer( node.right );
                        js++;
                    }
                }

            }
            if(!floor.isEmpty()){
                result.add( floor );
            }
            count = js;
        }
        return result;
    }

    private List<List<Integer>> method1( TreeNode root ) {
        List<List<Integer>> result = new LinkedList<>(  );
        Queue<TreeNode> queue = new LinkedList<>(  );
        queue.offer( root );
        int count = 0;
        while(!queue.isEmpty()){
            List<Integer> floor = new LinkedList<>(  );
            for(int i=0;i<Math.pow( 2,count );i++){
                TreeNode node = queue.poll();
                if(null!=node){
                    floor.add( node.val );
                    queue.offer( node.left );
                    queue.offer( node.right );
                }else{
                    queue.offer( null );
                    queue.offer( null );
                }

            }
            if(!floor.isEmpty()){
                result.add( floor );
            }
            count++;
        }
        return result;
    }
}
