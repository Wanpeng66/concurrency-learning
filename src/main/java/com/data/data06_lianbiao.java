package com.data;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

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
        AtomicInteger flag = new AtomicInteger( 0 );
        System.out.println();



        ListNode head = new ListNode( 1 );
        head.next = new ListNode( 2 );
        head.next.next = new ListNode( 3 );
        head.next.next.next = new ListNode( 2 );
        head.next.next.next.next = new ListNode( 0 );
        //System.out.println(check( head ));
        //reverse( head );
        head.next.next.next.next.next = head;

        System.out.println(checkCycle( head ));
    }

    //单链表反转
    public static void reverse(ListNode head){
          ListNode pre = null;
          ListNode next = null;
          ListNode current = head;

          if(null==head){
              return ;
          }
          while(null!=current){
              log.info( current.val+"" );
              next = current.next;
              current.next = pre;
              pre = current;
              current = next;

          }
          log.info( "---------------------------------" );
          while(null!=pre){
              log.info( pre.val+"" );
              pre = pre.next;
          }

    }

    //判断单链表是否有环（下面写法少了一种情况:链表某一段(节点数大于1)成环,普遍使用快慢指针法判断）
    public static boolean checkCycle(ListNode head){
          ListNode current = head;
          ListNode next = null;

          if(null==head||null==head.next)
              return false;

          while(null!=current){
              next = current.next;
              if (next == current)
                  return true;
              if(next == head)
                  return true;
              current = next;
          }

          return false;

    }

}
