package com.data;

/**
 * @author: wp
 * @Title: data40_dynamic
 * @Description:
 * @date 2020/4/29 19:52
 */
public class data42_dynamic {
    int min = Integer.MAX_VALUE;
    public int lwstBT(String s1,String s2){

        int n = s1.length();
        int m = s2.length();
        char[] c1 = s1.toCharArray();
        char[] c2 = s2.toCharArray();
        lwst(c1,n,c2,m,0,0,0);
        return min;

    }

    private void lwst( char[] c1, int n, char[] c2, int m, int i, int j, int edit ) {
        if(i==n || j==m){
            if(i<n){
                edit += (n-i);
            }
            if(j<m){
                edit += (m-j);
            }
            if(edit<min){
                min = edit;
            }
            return;
        }
        if(c1[i]==c2[j]){
            lwst( c1,n,c2,m,i+1,j+1,edit );
        }else{
            lwst( c1,n,c2,m,i+1,j+1,edit+1 );
            lwst( c1,n,c2,m,i+1,j,edit+1 );
            lwst( c1,n,c2,m,i,j+1,edit+1 );

        }

    }

    public static void main( String[] args ) {
        data42_dynamic d = new data42_dynamic();
        int i = d.lwstBT( "facebook", "faaebool" );
        System.out.println(i);
    }

}
