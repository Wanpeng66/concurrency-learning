package com.leetCode;

import javax.tools.ToolProvider;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author: wp
 * @Title: P111
 * @Description: 求二叉树的最小深度
 * @date 2020/4/1 16:32
 */
public class P111 {
    static int deep = 0;
    public static int minDepth(TreeNode root) {
        if(root==null){
            return 0;
        }else if(root.left==null&&root.right==null){
            return 1;
        }
        int left = minDepth( root.left );
        int right = minDepth( root.right );
        if(left==0&&right==0){
            return 1;
        }else if(left==0){
            return right+1;
        }else if(right==0){
            return left+1;
        }else{
            return Math.min( left,right )+1;
        }


    }

    private static int Method1( TreeNode root ) {
        if(root==null){
            return 0;
        }else if(root.left==null&&root.right==null){
            return 1;
        }
        int tmp = 0;

        FindMin(root.left,tmp+1);
        FindMin(root.right,tmp+1);

        return deep+1;
    }

    private static void FindMin( TreeNode node, int tmp ) {
        if(node == null){
            tmp--;
            return ;
        }
        if(node.left==null&&node.right==null){
            if(tmp>0){
                if(deep==0){
                    deep = tmp;
                }else if(tmp < deep){
                    deep  = tmp;
                }
            }
            tmp--;
            return;
        }


        FindMin( node.left,tmp+1 );
        FindMin( node.right,tmp+1 );
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

    public static void main( String[] args ) {
        String in = "[1,2,3,4,null,null,5]";
        TreeNode root = stringToTreeNode( in );
        System.out.println(minDepth( root ));

    }
}
