package com.data;

/**
 * @author: wp
 * @Title: data23_binaryTree
 * @Description: 树以及二叉树简介
 * @date 2020/3/30 21:02
 */
public class data23_binaryTree {
    public void bl(Object leftNode,Object rightNode){
        if(leftNode==null&&rightNode==null){
            return;
        }
        if(leftNode!=null){
            System.out.println(leftNode);
        }
        if(rightNode!=null){
            System.out.println(rightNode);
        }
        if(leftNode!=null){
            bl(leftNode.toString(),leftNode.toString());
        }
        if(rightNode!=null){
            bl(rightNode.toString(),rightNode.toString());
        }

    }
}
