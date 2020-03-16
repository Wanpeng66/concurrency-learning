package com.data;

import java.util.Arrays;

/**
 * @author: wp
 * @Title: data11_sort
 * @Description: 排序算法分析：冒泡、插入、选择
 * @date 2020/3/11 20:25
 */
public class data11_sort {

    //冒泡排序 实际很少用，用插入排序的多
    private static void bubbleSort(int[] array){
        boolean flag = true;
        for(int i=0;i<array.length;i++){     //O(n)
            if(flag){
                flag = false;
                for(int k=0;k<array.length-i-1;k++){   //O(n)
                    if(array[k]>array[k+1]){
                        int tmp = array[k];
                        array[k] = array[k+1];
                        array[k+1] = tmp;
                        flag = true;
                    }
                }
            }else {
                break;
            }

        }
    }

    //比较蠢的插入排序写法
    private static void insertionSort1(int[] array){
        //[2,3,4,1]
        for(int i=1;i<array.length;i++){
            for(int k=0;k<i;k++){
                if(array[k]>array[i]){
                    int tmp = array[i];
                    for(int j=i;j>k;j--){
                        array[j]=array[j-1];
                    }
                    array[k] = tmp;
                }
            }
        }
    }

    //标准的插入排序写法
    private static void insertionSort2(int[] array){
        //[2,3,4,1]
        for(int i=1;i<array.length;i++){     //O(n)
            int tmp = array[i];
            int j = i-1;
            for(;j>=0;j--){                   //O(n)
                if(array[j]>tmp){
                    array[j+1] = array[j];
                }else{
                    break;
                }
            }
            array[j+1] = tmp;
        }
    }

    //选择排序 实际很少用，用插入排序的多
    private static void selectionSort(int[] array){
        for(int i=0;i<array.length;i++){            //O(n)
            int min = array[i];
            int index = i;
            for(int j=i+1;j<array.length;j++){     //O(n)
                if(array[j]<min){
                    min = array[j];
                    index = j;
                }
            }
            array[index] = array[i];
            array[i] = min;
        }
    }

    //归并排序:采用分治的思想,将要排序的数组分成更小数组，然后依次合并
    //它的精力是放在合并上
    private static void mergeSort(int[] array){
        int size = array.length;
        merge_sort(array,0,size-1);
    }

    private static void merge_sort( int[] array, int begin, int end ) {
        //临界条件
        if(begin>=end) {
            return ;
        }
        //如何拆分
        int mid = (begin+end)/2;
        int[] r1 = new int[mid-begin+1];
        int[] r2 = new int[end-mid];
        System.arraycopy( array,begin,r1,0,mid-begin+1);
        System.arraycopy( array,mid+1,r2,0,end-mid);
        //之所以递归调用，就是为了拿到最小粒度的子数组,然后对子数组进行合并以及排序返回
        merge_sort( r1,0,r1.length-1 );
        merge_sort( r2,0,r2.length-1 );

        //合并结果
        merge(array,r1,r2);

    }

    private static void merge( int[] array, int[] r1, int[] r2 ) {
        int k=0 ,x = 0,index = 0;
        while(k<r1.length&&x<r2.length){
            if(r1[k]>=r2[x]){
                array[index] = r2[x];
                x++;
            }else{
                array[index] = r1[k];
                k++;
            }
            index++;
        }
        if(k>=r1.length){
            for(;x<r2.length;x++){
                array[index] = r2[x];
                index++;
            }
        }else if(x>=r2.length){
            for(;k<r1.length;k++){
                array[index] = r1[k];
                index++;
            }
        }
    }

    //快速排序，也是分治思想，它的精力是放在拆分上
    private static void quickSort(int[] array){
        int length = array.length;
        quick_sort(array,0,length-1);
    }

    private static void quick_sort( int[] array, int begin, int end ) {
        if(begin>=end){
            return ;
        }
        int m = partition(array,begin,end);
        quick_sort( array,begin,m-1 );
        quick_sort( array,m+1,end );
    }

    private static int partition( int[] array, int begin, int end ) {
        int s = array[end];
        int i = begin;
        for(int k=begin;k<=end;k++){
            if(array[k]<s){
                int tmp = array[i];
                array[i] = array[k];
                array[k] = tmp;
                i++;
            }
        }
        array[end] = array[i];
        array[i] = s;
        return i;
    }

    //在O(n)时间内找到一个集合内第k大元素
    private static int findK(int[] array,int k){
        int length = array.length;
        return recursion_Find(array,0,length-1,k);
    }

    private static int recursion_Find( int[] array, int begin, int end,int target ) {
        if(begin>=end){
            return begin;
        }
        int m = partition( array,begin,end );
        if(m+1==target){
            return m+1;
        }else if(m+1<target){
            return recursion_Find( array,m+1,end,target );
        }else{
            return recursion_Find( array,begin,m,target );
        }
    }

    public static void main( String[] args ) {
        int[] array = {2,5,4,100,8,1,0,5,10,40,20};
        //quickSort( array );
        for (int i : array) {
            System.out.println(i);
        }
        System.out.println("--------------------------------");
        int k = findK( array, 5 );
        System.out.println(array[k]);

    }

}
class SortQuestion{
    public static void main( String[] args ) {
        
    }
}
