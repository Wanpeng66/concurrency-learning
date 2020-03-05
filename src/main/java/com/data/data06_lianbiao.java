package com.data;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: wp
 * @Title: data06_lianbiao
 * @Description: 链表  字符串是通过单链表来存储的，那该如何来判断是一个回文串
 * @date 2020/3/5 19:22
 */
@Slf4j
public class data06_lianbiao {

      public static class ListNode {
          int val;
          ListNode next;

          ListNode( int x ) {
              val = x;
          }
      }

      public static boolean check(ListNode head){
          ListNode fast = head;
          ListNode slow = head;
          ListNode pre = null;

          if(null!=head&&null==head.next){
              return true;
          }
          do{
              fast = fast.next.next;
              ListNode next = slow.next;
              slow.next = pre;
              pre = slow;
              slow = next;
              log.info( "---------" );
              log.info( "pre={}",null!=pre?pre.val:"null" );
              log.info( "slow={}",null!=slow?slow.val:"null" );
              log.info( "fast={}",null!=fast?fast.val:"null" );
          }while(null!=fast&&null!=fast.next);
          //如果链表深度为奇数（此时fast不为null），slow节点应该在中间节点的下一节点
          if(null!=fast){
            slow = slow.next;
          }
          //pre往前走，slow往后走，依次比较每个节点的值是否一样，只要有不一样的节点则false
          while(null!=slow){
              if(pre.val != slow.val){
                  return false;
              }
              pre = pre.next;
              slow = slow.next;
          }
          return true;
      }

    public static void main( String[] args ) {
        ListNode head = new ListNode( 1 );
        head.next = new ListNode( 2 );
        head.next.next = new ListNode( 3 );
        head.next.next.next = new ListNode( 2 );
        head.next.next.next.next = new ListNode( 0 );
        System.out.println(check( head ));
    }

}
