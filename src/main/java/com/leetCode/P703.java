package com.leetCode;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author: wp
 * @Title: P703
 * @Description:
 * 设计一个找到数据流中第K大元素的类（class）。注意是排序后的第K大元素，不是第K个不同的元素。
 * 你的 KthLargest 类需要一个同时接收整数 k 和整数数组nums 的构造器，
 * 它包含数据流中的初始元素。每次调用 KthLargest.add，返回当前数据流中第K大的元素。
 * @date 2020/4/2 19:27
 */
public class P703 {
    int k = 0;
    int [] otherHeap = null;
    public P703(int k, int[] nums) {
        this.k = k;
        buildHeap(k,nums);
    }

    private void buildHeap( int k, int[] nums ) {
        int size = nums.length;
        if(size<k){
            otherHeap = new int[size+1];
            for(int i=0;i<size;i++){
                otherHeap[i+1] = nums[i] ;
            }
            //构建小顶堆
            buildSmallHeap(otherHeap);
        }else{
            otherHeap = new int[k+1];
            for(int i=0;i<k;i++){
                otherHeap[i+1] = nums[i];
            }
            //构建k个元素的小顶堆
            buildSmallHeap(otherHeap);
            for(int i=k;i<size;i++){
                if(nums[i]>otherHeap[1]){
                    otherHeap[1] = nums[i];
                    heapifyOfSmall( otherHeap );
                }

            }
        }

    }


    ///自上而下构建堆
    private void buildSmallHeap( int[] otherHeap ) {
        int size = otherHeap.length;
        for(int i=size/2;i>0;i--){
            int index = i;
            while(true){
                int minPos = index;
                if(2*index<size && otherHeap[2*index]<otherHeap[index]){
                    minPos = 2*index;
                }
                if(2*index+1<size && otherHeap[2*index+1]<otherHeap[minPos]){
                    minPos = 2*index+1;
                }
                if(minPos==index){
                    break;
                }
                swap( otherHeap,minPos,index );
                index = minPos;

            }
        }
    }

    private void swap( int[] array, int maxPos, int index ) {
        int tmp = array[maxPos];
        array[maxPos] = array[index];
        array[index] = tmp;
    }

    public int add(int val) {
        int j = otherHeap.length;
        //如果小顶堆实际元素小于k个，则插入小顶堆
        if(j<k+1){
            int[] tmp = new int[j+1];
            for(int i=0;i<j;i++){
                tmp[i] = otherHeap[i];
            }
            tmp[tmp.length-1] = val;
            //插入新叶子节点进行堆化
            heapifyOfSmall2(tmp);
            otherHeap = tmp;

        }else if(val>otherHeap[1] ){
            //如果元素大于小顶堆堆顶元素,则替换堆顶元素并堆化
           otherHeap[1] = val;
            //插入新叶子节点进行堆化
            heapifyOfSmall(otherHeap);

        }
        return otherHeap[1];
    }

    //小顶堆更新了堆顶元素，所以进行自上而下的堆化
    private void heapifyOfSmall( int[] otherHeap) {
        int index = 1;
        int size = otherHeap.length;
        while(true){
            int minPos = index;
            if(2*index<size && otherHeap[2*index]<otherHeap[index]){
                minPos = 2*index;
            }
            if(2*index+1<size && otherHeap[2*index+1]<otherHeap[minPos]){
                minPos = 2*index+1;
            }
            if(index/2>0 && otherHeap[index/2]>otherHeap[index]){
                minPos = index/2;
            }
            if(minPos == index){
                break;
            }
            swap( otherHeap,minPos,index );
            index = minPos;
        }
    }

    //小顶堆插入了新的叶子节点，所以进行自下而上的堆化
    private void heapifyOfSmall2( int[] otherHeap) {
        int index = otherHeap.length-1;
        while(true){
            int minPos = index;
            if(index/2>0 && otherHeap[index/2]>otherHeap[index]){
                minPos = index/2;
            }
            if(minPos == index){
                break;
            }
            swap( otherHeap,minPos,index );
            index = minPos;
        }
    }

    public static void main( String[] args ) {
       int[] array = {};
       int k = 1;
        P703 p = new P703( k,array );
        System.out.println(p.add( -3 ));
        System.out.println(p.add( -2 ));
        System.out.println(p.add( -4 ));
        System.out.println(p.add( 0 ));
        System.out.println(p.add( 4 ));


    }
}
class P703_2{
    List<Integer> items = null;
    int k = 0;
    public P703_2(int k, int[] nums) {
        this.k = k;
        items = Arrays.stream( nums ).boxed().collect( Collectors.toList());
    }

    public int add(int val) {
        items.add( val );
        int size = items.size();
        //进行快排
        quickSort(items,0,size-1);
        return items.get( items.size()-k );


    }

    private void quickSort( List<Integer> items, int start, int end ) {
        if(start>=end){
            return;
        }
        int partition = partion(items,start,end);
        quickSort( items,start,partition-1 );
        quickSort( items,partition+1,end );
    }

    private int partion( List<Integer> items, int start, int end ) {
        int s = items.get( end );
        int i = start;
        for(int k=start;k<=end;k++){
            if(items.get( k )<s){
                int tmp = items.get( i );
                items.set( i,items.get( k ) );
                items.set( k,tmp );
                i++;
            }
        }
        Integer val = items.get( i );
        items.set( end,val );
        items.set( i,s );
        return i;

    }
    public static void main( String[] args ) {
        int[] array = {4,5,8,2};
        int k = 3;
        P703_2 p = new P703_2( k,array );
        System.out.println(p.add( 3 ));
        System.out.println(p.add( 5 ));
        System.out.println(p.add( 10 ));
        System.out.println(p.add( 9 ));
        System.out.println(p.add( 4 ));


    }


}
