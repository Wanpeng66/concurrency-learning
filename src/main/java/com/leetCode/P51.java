package com.leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: wp
 * @Title: P51
 * @Description: 51.N皇后
 * @date 2020/4/23 16:18
 */
public class P51 {

    public List<List<String>> solveNQueens( int n) {
        List<List<Integer>> res = new LinkedList<>(  );
        List<Integer> l = new ArrayList<>( n );
        for (int i = 0; i < n; i++) {
            l.add( -1 );
        }
        solve(0,n,l,res);

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

    private void solve( int row, int n, List<Integer> l, List<List<Integer>> res ) {
        if(row>=n){
            res.add( new ArrayList<>( l ) );
            return;
        }
        for (int i = 0; i < n; i++) {
            if(isOk(row,i,n,l)){
                l.set( row,i );
                int tmp = row;
                solve( ++tmp,n,l, res );
            }
        }

    }

    private boolean isOk( int row, int col,int n, List<Integer> l ) {
        int left = col - 1;
        int right = col + 1;
        while(--row>=0){
            if(l.get( row )==col) return false;
            if(left>=0 && l.get( row ) == left) return false;
            if(right<=n && l.get( row )==right) return false;
            left--;right++;
        }
        return true;
    }
    public static void main( String[] args ) {
        P51 p = new P51();
        List<List<String>> lists = p.solveNQueens( 4 );
        System.out.println(lists.toString());
    }

}
