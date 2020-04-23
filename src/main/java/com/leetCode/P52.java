package com.leetCode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: wp
 * @Title: P52
 * @Description: 52. N皇后 II
 * @date 2020/4/23 16:32
 */
public class P52 {
    public int totalNQueens( int n) {
        List<List<Integer>> res = new LinkedList<>(  );
        List<Integer> l = new ArrayList<>( n );
        for (int i = 0; i < n; i++) {
            l.add( -1 );
        }
        solve(0,n,l,res);
        return res.size();
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
}
