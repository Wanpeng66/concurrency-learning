package com.leetCode;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: wp
 * @Title: P208
 * @Description: 208. 实现 Trie (前缀树)
 * 实现一个 Trie (前缀树)，包含 insert, search, 和 startsWith 这三个操作
 * @date 2020/4/12 22:40
 */
public class P208 {
    private TrieNode root;

    public P208() {
        root = new TrieNode( '/' );
        root.setNodes( new HashMap<>( 26 ) );
    }

    public void insert(String word) {
        if(null==word || word.equals( "" )){
            return;
        }
        TrieNode node = root;
        char[] chars = word.toCharArray();
        for (char cr :chars){
            if(node.getNodes().containsKey( cr )){
                TrieNode child = node.nodes.get( cr );
                node = child;
            }else{
                TrieNode child = new TrieNode( cr );
                node.getNodes().put( cr,child );
                node = child;
            }
        }
        node.setEnding( true );

    }

    public boolean search(String word) {
        if(null==word || word.equals( "" )){
            return false;
        }
        TrieNode node = root;
        char[] chars = word.toCharArray();
        for (char cr : chars) {
            if(!node.getNodes().containsKey( cr )){
                return false;
            }else{
                TrieNode trieNode = node.getNodes().get( cr );
                node = trieNode;
            }
        }
        if(!node.isEnding){
            return false;
        }
        return true;
    }

    public boolean startsWith(String prefix) {
        if(null==prefix || prefix.equals( "" )){
            return false;
        }
        TrieNode node = root;
        char[] chars = prefix.toCharArray();
        for (char cr : chars) {
            if(!node.getNodes().containsKey( cr )){
                return false;
            }else{
                TrieNode trieNode = node.getNodes().get( cr );
                node = trieNode;
            }
        }
        return true;
    }

    public static void main( String[] args ) {
        P208 p = new P208();
        String[] strs = {"Trie","insert","search","search","startsWith","insert","search"};
        for (String str : strs) {
            p.insert( str );
        }
    }
}
class TrieNode{
    char val;
    Map<Character,TrieNode> nodes ;
    boolean isEnding;

    public TrieNode( char val ) {
        this.val = val;
        nodes = new HashMap<>( 26 );
    }

    public char getVal() {
        return val;
    }

    public void setVal( char val ) {
        this.val = val;
    }

    public Map<Character, TrieNode> getNodes() {
        return nodes;
    }

    public void setNodes( HashMap<Character, TrieNode> nodes ) {
        this.nodes = nodes;
    }

    public boolean isEnding() {
        return isEnding;
    }

    public void setEnding( boolean ending ) {
        isEnding = ending;
    }
}
