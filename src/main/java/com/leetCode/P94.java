package com.leetCode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

/**
 * @author: wp
 * @Title: P94
 * @Description: 94. 二叉树的中序遍历
 * @date 2020/4/9 21:26
 */
public class P94 {

    public List<Integer> inorderTraversal( TreeNode root) {
        return bfsWithColor( root );
    }

    private List<Integer> bfsWithColor( TreeNode root ) {
        List<Integer> result = new LinkedList<>(  );
        Stack<ColorNode> stack = new Stack<>();
        stack.push( new ColorNode(0,root) );
        while(!stack.isEmpty()){
            ColorNode node = stack.pop();
            if(null==node.getNode()){
                continue;
            }
            if(node.getColor()==0){
                stack.push( new ColorNode( 0,node.getNode().right ) );
                stack.push( new ColorNode( 1,node.getNode() ) );
                stack.push( new ColorNode( 0,node.getNode().left ) );
            }else{
                result.add(node.getNode().val);
            }
        }
        return result;
    }

    private List<Integer> bfsWithStack( TreeNode root ) {
        List < Integer > res = new ArrayList< >();
        Stack< TreeNode > stack = new Stack < > ();
        TreeNode curr = root;
        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }
        return res;
    }

    private List<Integer> dfs( TreeNode root ) {
        List<Integer> result = new LinkedList<>(  );
        if(null==root){
            return result;
        }
        recursion(root.left,result);
        result.add( root.val );
        recursion(root.right,result);

        return result;
    }

    private void recursion( TreeNode node, List<Integer> result ) {
        if(null==node){
            return ;
        }
        recursion(node.left,result);
        result.add( node.val );
        recursion(node.right,result);
    }


}


class ColorNode{
    int color;
    TreeNode node;

    public ColorNode( int color, TreeNode node ) {
        this.color = color;
        this.node = node;
    }

    public ColorNode() {
    }


    public int getColor() {
        return color;
    }

    public void setColor( int color ) {
        this.color = color;
    }

    public TreeNode getNode() {
        return node;
    }

    public void setNode( TreeNode node ) {
        this.node = node;
    }
}