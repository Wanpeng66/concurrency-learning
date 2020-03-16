package com.data;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * @author: wp
 * @Title: data13_sort
 * @Description: TODO 桶排序、计数排序、基数排序
 * @date 2020/3/16 20:42
 */
public class data13_sort {

    /**
    * @Author wp
    * @Description  计数排序只能用在数据范围不大的场景中，
     * 如果数据范围 k 比要排序的数据 n 大很多，就不适合用计数排序了。
     * 而且，计数排序只能给非负整数排序，
     * 如果要排序的数 据是其他类型的，要将其在不改变相对大小的情况下，转化为非负整数。
    * @Date  2020/3/16 21:17
    * @Param  * @param array
 * @param n
    * @return void
    */
    private static void counting_sort(int[] array,int n){
        int length = array.length;
        int max = array[0];
        //先计算最大的数
        for(int i=1;i<length;i++){
            if(array[i]>max){
                max = array[i];
            }
        }

        //定义一个临时数组用来存储元素的个数
        int[] count = new int[max+1];
        for(int i=0;i<max+1;i++){
            count[i] = 0;
        }

        //统计每个元素的个数
        for(int k=0;k<length;k++){
            count[array[k]]++;
        }

        //最骚的依次累加,可以知道每个元素在排序后的下标
        for(int i=1;i<max+1;i++){
            count[i] = count[i-1]+count[i];
        }

        //定义一个临时数组
        int[] tmp = new int[length];
        //从count数组中拿到下标，依次放进临时数组中，然后将下标减一
        for(int i=0;i<length;i++){
            int val = array[i];
            int index = count[val];
            tmp[index-1] = val;
            count[val] = index -1;
        }

        for(int i=0;i<length;i++){
            array[i] = tmp[i];
        }


    }


    //桶排序
    private static void bucket_sort(int[] array,int n,int capacity){
        int max = array[0];
        for(int i=1;i<n;i++){
            if(array[i]>max){
                max = array[i];
            }
        }
        Map<Integer, List<Integer>> buckets = new HashMap<>( max/capacity+1 );
        final int[] count = {0};
        int sum = 0;
        while(true){
            buckets.put( sum,new ArrayList<Integer>(capacity) );
            count[0]++;
            if((sum+=capacity) >= max){
                buckets.put( sum,new ArrayList<Integer>(capacity) );
                break;
            }

        }
        for (int i : array) {
            int key = (i/capacity)*capacity;
            buckets.get( key ).add( i );
        }
        count[0] = 0;
        buckets.forEach( new BiConsumer<Integer, List<Integer>>() {
            @Override
            public void accept( Integer key, List<Integer> integers ) {
                int[] arr = new int[integers.size()];
                for (int i = 0; i < integers.size(); i++) {
                    arr[i] = integers.get( i );
                }
                data11_sort.mergeSort( arr );
                for (int i = 0; i < arr.length; i++) {
                    array[count[0]] = arr[i];
                    count[0] ++;
                }
            }
        } );



    }

    public static void main( String[] args ) {
        int[] array = {2,5,3,0,2,3,0,3};
        //counting_sort( array,8 );
        bucket_sort(array,8,3);
        for (int i : array) {
            System.out.println(i);
        }
    }
}
