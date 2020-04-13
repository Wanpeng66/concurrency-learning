package com.leetCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author: wp
 * @Title: P745
 * @Description:
 * @date 2020/4/13 21:30
 */
public class P745 {
    TrieNode pre = new TrieNode( '/' );
    TrieNode suf = new TrieNode( '/' );
    String[] words ;
    public P745(String[] words) {
        buildTrie( words );
        this.words = words;
    }

    private void buildTrie( String[] words ) {
        String[] suffixs = new String[words.length];
        for (int i = 0; i < words.length; i++) {
            char[] chars = words[i].toCharArray();
            //先构建前缀字典树
            TrieNode node = pre;
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
            //反转字符串，构建后缀数
            String reverse = reverse( words[i] );
            node = suf;
            chars = reverse.toCharArray();
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

        }
    }

    public int f(String prefix, String suffix) {

        if(null==prefix){
            return -1;
        }
        if(null==suffix ){
            return -1;
        }
        if(prefix.equals( "" )&&suffix.equals( "" )){
            return 0;
        }
        char[] chars = prefix.toCharArray();
        for (char cr : chars) {
            if(pre.getNodes().containsKey( cr )){
                TrieNode trieNode = pre.getNodes().get( cr );
                pre = trieNode;
            }else{
                return -1;
            }
        }
        chars = suffix.toCharArray();
        for (char cr : chars) {
            if(suf.getNodes().containsKey( cr )){
                TrieNode trieNode = suf.getNodes().get( cr );
                suf = trieNode;
            }else{
                return -1;
            }
        }
        int index = -1;
        for(int i=0;i<words.length;i++){
            String str = words[i];
            if(str.length()<prefix.length() || str.length() < suffix.length()){
                continue;
            }
            if(prefix.equals( "" )&&str.endsWith( suffix )){
                index = i;
            }else if(suffix.endsWith( "" )&&str.startsWith( prefix )){
                index = i;
            }else if(str.startsWith(prefix)&&str.endsWith(suffix)){
                index = i;
            }
        }
        return index;
    }

    public String reverse(String str) {
        if (str == null || str.equals("")){
            return str;
        }
        List< Character > list = new ArrayList< Character >();
        for (char c: str.toCharArray()){
            list.add(c);
        }
        Collections.reverse(list);
        StringBuilder builder = new StringBuilder(list.size());
        for (Character c: list){
            builder.append(c);
        }
        return builder.toString();
    }

    public static void main( String[] args ) {
        String[] words = {"pop"};
        P745 p = new P745( words );
        int pop = p.f( "", "pop" );
    }
}
