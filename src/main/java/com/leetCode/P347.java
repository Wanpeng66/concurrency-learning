package com.leetCode;

import java.util.*;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

/**
 * @author: wp
 * @Title: P347
 * @Description: 347. 前K个高频元素
 * @date 2020/4/3 22:09
 */
public class P347 {
    public List<Integer> topKFrequent( int[] nums, int k) {
        Map<Integer,Integer> counter = new HashMap<>( nums.length );
        for (int num : nums) {
            if(counter.containsKey( num )){
                Integer val = counter.get( num )+1;
                counter.put( num,val );
            }else{
                counter.put( num,1 );
            }
        }
        PriorityQueue<Map.Entry> topK = new PriorityQueue( ( o1, o2 ) -> {
            return (Integer)(( Map.Entry)o1).getValue() - (Integer)(( Map.Entry)o2).getValue();
        } );
        Iterator<Map.Entry<Integer, Integer>> iterator = counter.entrySet().iterator();
        while(iterator.hasNext()){
            topK.offer( iterator.next() );
            if(topK.size()>k){
                topK.poll();
            }
        }
        List<Integer> collect = new ArrayList<>( k );
        int index = 0;
        while(!topK.isEmpty()){
            collect.add( index,(Integer) topK.poll().getKey() );
            index++;
        }
        Collections.reverse( collect );
        return collect;
    }

    public static void main( String[] args ) {
        P347 p = new P347();
        int[] array = {1,1,1,2,2,3};
        int k = 2;
        List<Integer> integers = p.topKFrequent( array, k );
        System.out.println(integers.toString());
    }
}
