package com.leetCode;

/**
 * @author: wp
 * @Title: P466
 * @Description: 统计重复个数
 * @date 2020/4/19 21:33
 */
public class P466 {
    public int getMaxRepetitions(String s1, int n1, String s2, int n2) {
        StringBuffer sbf = new StringBuffer(  );
        for (int i = 0; i < n1; i++) {
            sbf.append( s1 );
        }
        s1 = sbf.toString();
        sbf = new StringBuffer(  );
        for (int i = 0; i < n2; i++) {
            sbf.append( s2 );
        }
        s2 = sbf.toString();
        int index = 0;
        int link = 0;
        char[] chars = s2.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            char var = chars[i];
            int tmp = s1.indexOf( var, index );
            if(tmp!=-1){
                index = tmp+1;
            }else{
                return 0;
            }
        }
        while(s2.charAt( link )== s1.charAt( index ) && index < s1.length() ){
            index++;
            if(link>=s2.length()-1){
                link = 0;
            }else{
                link++;
            }
        }
        int count = s1.length()/ index;
        return count;
    }

    private int method1( String s1, int n1, String s2, int n2 ) {
        char[] chars = s2.toCharArray();
        int index = 0;
        for (int i = 0; i < chars.length; i++) {
            char var = chars[i];
            int tmp = s1.indexOf( var, index );
            if(tmp!=-1){
                index = tmp+1;
            }else{
                return tmp;
            }
        }

        if(n1==n2){
            return 1;
        }
        return (int) (Math.log(n1) / Math.log(n2));
    }

    public static void main( String[] args ) {
        String s1 = "niconiconi";
        int n1 = 99981;
        String s2 = "nico";
        int n2 = 81;
        P466 p = new P466();
        int maxRepetitions = p.getMaxRepetitions( s1, n1, s2, n2 );
        System.out.println( maxRepetitions );
    }
}
