package com.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: wp
 * @Title: data30_graph
 * @Description: 图的学习
 * @date 2020/4/7 19:28
 */
public class data30_graph {


}
//无向图
class MyGraph{
    int v;
    List<List<Integer>> list = null;

    MyGraph(int v){
        this.v = v;
        list = new ArrayList<>( v+1 );
        for(int i=0;i<v+1;i++){
            list.add( new LinkedList<Integer>(  ) );
        }
    }

    public void addNode(int base,int target){
        List<Integer> mine = list.get( base );
        mine.add( target );
        List<Integer> other = list.get( target );
        other.add( base );
    }
}
