package com.leetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author: wp
 * @Title: P648
 * @Description: 648. 单词替换
 * @date 2020/4/13 20:13
 */
public class P648 {
    public static void main( String[] args ) {
        P648 p = new P648();
        String[] dict = {"a", "aa", "aaa", "aaaa"};
        String sentence = "a aa a aaaa aaa aaa aaa aaaaaa bbb baba ababa";
        String s = p.replaceWords( Arrays.asList( dict ), sentence );
        System.out.println(s);
    }

    public String replaceWords( List<String> dict, String sentence) {
        TrieNode root = buildTrie(dict);
        StringBuffer sbf = new StringBuffer(  );
        String[] split = sentence.split( " " );
        for (String str : split) {
            String tmp = findRoot(str,root);
            sbf.append( tmp ).append( " " );
        }

        return sbf.toString().substring( 0,sbf.toString().length()-1 );
    }

    private String findRoot( String str, TrieNode root ) {
        StringBuffer sb = new StringBuffer(  );
        char[] chars = str.toCharArray();
        for (char cr : chars) {
            if(root.getNodes().containsKey( cr )){
                TrieNode trieNode = root.getNodes().get( cr );
                sb.append( cr );
                root = trieNode;
                if(trieNode.isEnding){
                    break;
                }

            }
            break;
        }

        if(root.isEnding && sb.length()>0){
            return sb.toString();
        }else{
            return str;
        }
    }

    private TrieNode buildTrie( List<String> dict ) {
        TrieNode root = new TrieNode( '/' );
        for (String str : dict) {
            char[] chars = str.toCharArray();
            TrieNode node = root;
            for (char cr : chars) {
                if(node.getNodes().containsKey( cr )){
                    TrieNode trieNode = node.getNodes().get( cr );
                    node = trieNode;
                }else{
                    TrieNode child = new TrieNode( cr );
                    node.getNodes().put( cr,child );
                    node = child;
                }
            }
            node.setEnding( true );
        }
        return root;
    }

    private String method1( List<String> dict, String sentence ) {
        String[] split = sentence.split( " " );
        StringBuffer sbf = new StringBuffer(  );
        for (String str : split) {
            String root = str;
            for (String s : dict) {
                if(str.startsWith( s ) || str.endsWith( s )){
                    if(root.length()>s.length()){
                        root = s;
                    }
                }
            }
            sbf.append( root ).append( " " );
        }
        return sbf.toString().substring( 0,sbf.toString().length()-1 );
    }

}
