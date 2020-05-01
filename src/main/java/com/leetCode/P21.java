package com.leetCode;

import lombok.Data;

import java.util.List;

/**
 * @author: wp
 * @Title: P21
 * @Description: TODO
 * @date 2020/5/1 13:48
 */
public class P21{
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode n1 = l1;
        ListNode n2 = l2;
        ListNode ln = new ListNode( Integer.MIN_VALUE );
        ListNode res = null;
        while(true){
            if(null==n1&&null==n2){
                break;
            }else if(null!=n1 && null!=n2){
                if(n1.val<n2.val){
                    ln.val = n1.val;
                    n1 = n1.next;
                }else{
                    ln.val = n2.val;
                    n2 = n2.next;
                }
            }else if(null==n1){
                ln.val = n2.val;
                n2 = n2.next;
            }else if(null==n2){
                ln.val = n1.val;
                n1 = n1.next;
            }
            if(null==res){
                res = ln;
            }
            if(null==n1&&null==n2){
                break;
            }
            ln.next = new ListNode( Integer.MIN_VALUE );
            ln = ln.next;
        }
        return res;
    }
    @Data
    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }

    public static void main( String[] args ) {
        P21 p = new P21();
        int[] x = {1,2,4};
        int[] y = {1,3,4};
        ListNode listNode = p.mergeTwoLists( p.ArraysToListNode( x ), p.ArraysToListNode( y ) );
    }

    private ListNode ArraysToListNode( int[] array ) {
        ListNode root = new ListNode( Integer.MIN_VALUE );
        ListNode res = null;
        for (int i = 0; i < array.length; i++) {
            root.val = array[i];
            if(null==res){
                res = root;
            }
            if(i!=array.length-1){
                root.next = new ListNode( Integer.MIN_VALUE );
                root = root.next;
            }

        }
        return res;
    }
}
