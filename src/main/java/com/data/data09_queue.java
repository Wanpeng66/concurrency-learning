package com.data;

import lombok.Data;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;

/**
 * @author: wp
 * @Title: data09_queue
 * @Description: 队列的学习
 * @date 2020/3/10 11:51
 */
public class data09_queue {

}
/**
* @Author wp
* @Description 数组实现的单端队列(顺序队列)
* @Date  2020/3/10 11:52
* @Param  * @param null
* @return
*/
class ArrayQueue<T>{
    private int capacity;
    private int head = 0;
    private int tail = 0;
    private Object[] datas = null;

    public ArrayQueue( int capacity ) {
        this.capacity = capacity;
        this.datas = new Object[capacity];
    }

    //出队
    public T dequeue(){
        if(head>=tail){
            return null;
        }
        T data = (T) datas[head];
        head++;
        return data;
    }

    //入队
    public boolean enqueue(T data){
        if(tail==capacity){
            if(head==0){
                return false;
            }
            for(int i=head;i<tail;i++){
                datas[i-head] = datas[i];
            }
            head = 0;
            tail = tail - head;
        }
            datas[tail] = data;
            tail++;
            return true;

    }


}

/**
* @Author wp
* @Description 链表实现的单端队列(链式队列)
* @Date  2020/3/10 13:17
* @Param  * @param null
* @return  
*/
class LinkedQueue<T>{
    @Data
    class Node<T>{
        private T data;
        private Node<T> next;

        public Node( T data ) {
            this.data = data;
        }
    }

    private Node<T> head;
    private Node<T> tail;

    public LinkedQueue( Node<T> head ) {
        this.head = head;
        this.tail = head;
    }

    //出队
    public T dequeue(){
        T data = null;
        if(null!=head){
            if(head==tail){
                data = head.getData();
                head = null;
                tail = null;
            }else{
                data = head.getData();
                head = head.getNext();
            }
        }
        return data;
    }

    //入队
    public boolean enqueue(T data){
        Node node = new Node(data);
        if(null==tail){
            if(null==head){
                head = node;
            }
            tail = node;
        }else{
            if(head==tail){
                head.setNext( node );
            }else{
                tail.setNext( node );
            }
            tail = node;
        }
        return true;
    }
}

/**
* @Author wp
* @Description 循环队列
* @Date  2020/3/10 20:32
* @Param  * @param null
* @return
*/
class CycleQueue{
    private int head;
    private int tail;
    private int capacity;
    private Object[] datas = null;

    public CycleQueue( int capacity ) {
        this.capacity = capacity;
        datas = new Object[capacity];
    }

    public Object dequeue(){
        if(head==tail){
            return null;
        }
        Object data = datas[head];
        head = (head+1)%capacity;
        return data;
    }

    public boolean enqueue(Object data){
        int tmp = (tail+1)%capacity;
        if(tmp == head){
            return false;
        }
        datas[tail] = data;
        tail = tmp;
        return true;
    }
}