package com.leetCode;

import javafx.util.Pair;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * @author: wp
 * @Title: P104
 * @Description: 求二叉树最大深度
 * @date 2020/3/31 15:43
 */
public class P104 {
    static int deep = 0;
    public static int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }else if(root.left==null&&root.right==null){
            return 1;
        }
        Stack<Pair<TreeNode,Integer>> stack = new Stack<>();
        stack.add( new Pair<>( root,1 ) );

        while(!stack.isEmpty()){
            Pair<TreeNode, Integer> pop = stack.pop();
            TreeNode node = pop.getKey();
            Integer tmp = pop.getValue();
            if(node!=null){
                deep = Math.max( tmp,deep );
                stack.add( new Pair<>( node.left,tmp+1 ) );
                stack.add( new Pair<>( node.right,tmp+1 ) );
            }
        }

        return deep;
    }

    //深度优先--递归
    private static int DFS( TreeNode root ) {
        if(root==null){
            return 0;
        }
        int left = DFS( root.left );
        int right = DFS( root.right );
        return Math.max( left,right )+1;
    }

    private static int Method1( TreeNode root ) {
        int tmp = 0;
        if(root==null){
            deep = 0;
        }else if(root.left==null&&root.right==null){
            deep = 1;
        }else{
            FindDeep(root.left,tmp+1);
            FindDeep(root.right,tmp+1);
        }
        return deep+1;
    }

    private static void FindDeep( TreeNode node, int tmp ) {
        if(node==null){
            tmp--;
            if(tmp>deep){
                deep = tmp;
            }
            return ;
        }
        FindDeep( node.left,tmp+1 );
        FindDeep( node.right,tmp+1 );
        tmp--;
    }

    public static void main( String[] args ) {
        String in = "[3,9,20,null,null,15,7]";
        TreeNode treeNode = stringToTreeNode( in );
        int i = maxDepth( treeNode );
        System.out.println(i);
    }

    public static TreeNode stringToTreeNode(String input) {
        input = input.trim();
        input = input.substring(1, input.length() - 1);
        if (input.length() == 0) {
            return null;
        }

        String[] parts = input.split(",");
        String item = parts[0];
        TreeNode root = new TreeNode(Integer.parseInt(item));
        Queue<TreeNode> nodeQueue = new LinkedList<>();
        nodeQueue.add(root);

        int index = 1;
        while(!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.remove();

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int leftNumber = Integer.parseInt(item);
                node.left = new TreeNode(leftNumber);
                nodeQueue.add(node.left);
            }

            if (index == parts.length) {
                break;
            }

            item = parts[index++];
            item = item.trim();
            if (!item.equals("null")) {
                int rightNumber = Integer.parseInt(item);
                node.right = new TreeNode(rightNumber);
                nodeQueue.add(node.right);
            }
        }
        return root;
    }
}
