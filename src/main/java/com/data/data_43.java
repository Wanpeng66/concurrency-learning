package com.data;

import com.google.common.hash.BloomFilter;
import lombok.Data;
import org.apache.commons.collections4.trie.PatriciaTrie;

import java.lang.management.GarbageCollectorMXBean;
import java.util.*;

/**
 * @author: wp
 * @Title: data_43
 * @Description: 拓扑排序
 * @date 2020/5/5 19:34
 */
public class data_43 {

    public static void main( String[] args ) {
        Graph graph = new Graph( 5 );
        graph.addEdge( 0,2 );
        graph.addEdge( 0,1 );
        graph.addEdge( 3,4 );
        graph.addEdge( 0,3 );
        graph.topSortByKahn();
    }
}
@Data
class Graph{
    int v;
    ArrayList<Integer>[] list;
    LinkedList<Edge>[] adjs;

    public Graph( int v) {
        this.v = v;
        this.list = new ArrayList[v];
        for (int i = 0; i < v; i++) {
            this.list[i] = new ArrayList<Integer>( v );
            this.adjs[i] = new LinkedList<Edge>(  );
        }
    }

    public void addEdge(int s, int t) {
          list[s].add(t);
    }
    public void addEdge(int s, int t,int w) {
        adjs[s].add( new Edge( s,t,w ) );
    }
    //khan算法 拓扑排序
    public void topSortByKahn(){
        int[] nums = new int[v];
        for (ArrayList<Integer> arrayList : list) {
            for (Integer val : arrayList) {
                nums[val]++;
            }
        }
        Queue<Integer> queue = new LinkedList<>(  );
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]==0){
                queue.offer( i );
            }
        }
        while(!queue.isEmpty()){
            Integer poll = queue.poll();
            System.out.println(poll);
            ArrayList<Integer> l = this.list[poll];
            for (Integer val : l) {
                nums[val] -= 1;
                if(nums[val]==0){
                    queue.offer( val );
                }
            }

        }
    }


}

@Data
class Edge{
    public int sid;
    public int eid;
    public int w;

    public Edge( int sid, int eid, int w ) {
        this.sid = sid;
        this.eid = eid;
        this.w = w;
    }
}

@Data
class Vertex{
    public int id;
    public int dist;

    public Vertex( int id, int dist ) {
        this.id = id;
        this.dist = dist;
    }
}
