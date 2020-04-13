package com.leetCode;

import java.util.*;

/**
 * @author: wp
 * @Title: P211
 * @Description: 211. 添加与搜索单词 - 数据结构设计
 * @date 2020/4/13 10:41
 */
public class P211 {
    TrieNode root = new TrieNode( '/' );
    Set<Integer> nums = new HashSet<>(  );
    public P211() {

    }

    public void addWord(String word) {
        if(null==word || word.equals( "" )){
            return;
        }
        TrieNode node = root;
        char[] chars = word.toCharArray();
        for (char cr : chars) {
            if(node.getNodes().containsKey( cr )){
                TrieNode trieNode = node.getNodes().get( cr );
                node = trieNode;
            }else{
                TrieNode trieNode = new TrieNode( cr );
                node.getNodes().put( cr,trieNode );
                node = trieNode;
            }
        }
        node.setEnding( true );
        nums.add( word.length() );
    }

    public boolean search(String word) {
        return method1( word );

    }

    private boolean method1( String word ) {
        if(null==word || word.equals( "" )){
            return false;
        }
        if(!nums.contains( word.length() )){
            return false;
        }
        TrieNode node = root;
        char[] chars = word.toCharArray();
        int k = 0;
        for (char cr : chars) {
            if(cr != '.'){
                if(node.getNodes().containsKey( cr )){
                    TrieNode trieNode = node.getNodes().get( cr );
                    node = trieNode;
                }else{
                    return false;
                }
            }else{
                Collection<TrieNode> values = node.getNodes().values();
                for (TrieNode value : values) {
                    char val = value.getVal();
                    String tmp = word.substring( k ).replaceFirst( ".", String.valueOf( val ) );
                    if(searchByDfs( tmp,node )){
                        return true;
                    }
                }
                break;
            }
            k++;

        }
        if(k==word.length()){
            return node.isEnding;
        }else{
            return false;
        }
    }

    private boolean searchByDfs( String word, TrieNode node ) {
        char[] chars = word.toCharArray();
        TrieNode child = node;
        int k = 0;
        for (char cr : chars) {
            if(cr != '.'){
                if(child.getNodes().containsKey( cr )){
                    TrieNode trieNode = child.getNodes().get( cr );
                    child = trieNode;
                }else{
                    return false;
                }
            }else{
                Collection<TrieNode> values = child.getNodes().values();
                for (TrieNode value : values) {
                    char val = value.getVal();
                    String tmp = word.substring( k ).replaceFirst( ".", String.valueOf( val ) );
                    if(searchByDfs( tmp,child )){
                        return true;
                    }
                }
                break;
            }
            k++;
        }
        if(k==word.length()){
            return child.isEnding;
        }else{
            return false;
        }
    }

    public static void main( String[] args ) {
        P211 p = new P211();
        p.addWord( "" );
        p.addWord( "bad" );
        p.addWord( "dad" );
        p.addWord( "mad" );
        boolean pad = p.search( "pad" );
        boolean bad = p.search( "bad" );
        boolean search = p.search( ".ad" );
        boolean search1 = p.search( "b.." );
    }
}
