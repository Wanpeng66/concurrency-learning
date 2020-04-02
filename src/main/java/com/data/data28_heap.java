package com.data;

/**
 * @author: wp
 * @Title: data28_heap
 * @Description: 堆以及堆排序
 * @date 2020/4/2 14:16
 */
public class data28_heap {
    /**
    * 自下而上的构建堆
    */
    public static void buildHeap1(int[] array){
        int size = array.length;
        for(int i=2;i<size;i++){
            method1(array,size,i);
        }

    }

    private static void method1( int[] array, int n, int i ) {
        if(n==0){
            return;
        }

        while(true){
            int maxpos = i;
            if(i/2>0&&array[i/2]>array[i]){
                maxpos = i/2;
            }
            if(maxpos==i){
                break;
            }
            int tmp = array[maxpos];
            array[maxpos] = array[i];
            array[i] = tmp;
            i = maxpos;
        }
    }

    /**
    * 自上而下构建堆
    */
    public static void buildHeap2(int[] array){
        int size = array.length;
        if(size==0){
            return ;
        }
        for(int i=size>>1;i>0;i--){
            method2(array,size,i);
        }

    }

    private static void method2( int[] array, int n, int i ) {
        while(true){
            int maxpos = i;
            if(i*2<n && array[2*i]<array[i]){
                maxpos = 2*i;
            }
            if(2*i+1<n && array[2*i+1]<array[maxpos]){
                maxpos = 2*i+1;
            }
            if(maxpos == i){
                break;
            }
            int tmp = array[maxpos];
            array[maxpos] = array[i];
            array[i] = tmp;
            i = maxpos;
        }
    }

    public static void sort(int[] array){
        int size = array.length;
        buildHeap2( array );
        for(int i=size-1;i>1;i--){
            heapSort(array,1,i);
        }
    }

    private static void heapSort( int[] array, int start, int end ) {
        int tmp = array[start];
        array[start] = array[end];
        array[end] = tmp;
        while(true){
            int minpos = start;
            if(start*2<end&&array[start*2]<array[start]){
                minpos = 2*start;
            }
            if(2*start+1<end&&array[2*start+1]<array[minpos]){
                minpos = 2*start+1;
            }
            if(minpos == start){
                break;
            }
            tmp = array[minpos];
            array[minpos] = array[start];
            array[start] = tmp;
            start = minpos;
        }
    }

    public static void main( String[] args ) {
        int[] array = {-1,2,3,1,6,11,4,0,8,10,222,44};
        buildHeap2( array );
        sort( array );
        for (int i : array) {
            System.out.println(i);
        }
    }
}
