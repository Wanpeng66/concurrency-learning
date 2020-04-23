package com.leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author: wp
 * @Title: P0812
 * @Description: 面试题 08.12. 八皇后
 * @date 2020/4/23 10:53
 */
public class P0812 {
    //存放所有的可能性排列的集合
    public List<List<Integer>> res = new LinkedList<>(  );

    public List<List<String>> solveNQueens( int n) {

        List<Integer> l = new ArrayList<>( n );
        for (int i = 0; i < n; i++) {
            l.add( -1 );
        }
        //进行排列组合
        solve(0,n,l);
        List<List<String>> ll = new LinkedList<>(  );
        //遍历所有的可能性
        for (List<Integer> list : res) {
            List<String> ls = new ArrayList<>( n );
            //每一个val值就是当前行的皇后所在列，进行字符串转换
            for (Integer val : list) {
                StringBuffer sbf = new StringBuffer(  );
                for (int i = 0; i < n; i++) {
                    if(i==val){
                        sbf.append( "Q" );
                    }else{
                        sbf.append( "." );
                    }
                }
                ls.add( sbf.toString() );
            }
            ll.add( ls );
        }
        return ll;
    }

    /**
    * @Param
     * @param row  第几行
     * @param n  全部的行/列数量（因为是正方形）
     * @param l 可能性集合
    */
    private void solve( int row, int n, List<Integer> l ) {
        //如果行数大于等于最大行，说明此时已经通过层层筛选，已有一种可能性，则存入全部的可能性集合中
        if(row>=n){
            res.add( new ArrayList<>( l ) ) ;
            return;
        }
        //每一行都有n种放法，那就遍历所有的可能
        for (int i = 0; i < n; i++) {
            //判断当前行的当前列是否满足条件(所在列没有其他皇后，并且对角线上也没有)
            //如果当前行的当前列满足条件，则进行下一行的排列(从第0列开始排列)
            //如果不满足条件，则进行当前行的下一列判断
            if(isOk(row,i,n,l)){
                l.set( row,i );
                int r = row;
                //进行下一行的遍历
                solve( ++r,n,l );
            }
        }
    }

    private boolean isOk( int row, int col,int n, List<Integer> l ) {
        int left = col-1;
        int right = col+1;
        //以当前列为基准，依次往上去判断
        while(--row>=0){
            //上一行的皇后所在的列等于当前列
            if(l.get( row )== col) return false;
            //上一行的皇后所在列在当前列的左上对角线上
            if(left>=0 && l.get( row )==left) return false;
            //上一行的皇后所在列在当前列的右上对角线上
            if(right<=n && l.get( row )==right) return false;
            left--;
            right++;
        }
        return true;
    }

    public static void main( String[] args ) {
        P0812 p = new P0812();
        List<List<String>> lists = p.solveNQueens( 4 );
        System.out.println(lists.toString());
    }

}
