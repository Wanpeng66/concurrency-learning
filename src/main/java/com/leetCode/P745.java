package com.leetCode;

import com.google.errorprone.annotations.Var;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: wp
 * @Title: P745
 * @Description:
 * @date 2020/4/13 21:30
 */
public class P745 {
    Map<String,Integer> suffixDicts = new HashMap<>(  );
    Map<String,Integer> cache = new HashMap<>( 16 );
    TrieNode pre = new TrieNode( '/' );
    TrieNode suf = new TrieNode( '/' );
    String[] words ;
    public P745(String[] words) {
        this.words = words;
        buildTrie( words );
        //build1( words );
    }
    private void buildTrie( String[] words ) {
        for (int i = 0; i < words.length; i++) {
            char[] chars = words[i].toCharArray();
            //先构建前缀字典树
            TrieNode node = pre;
            for (char cr : chars) {
                if(node.getNodes().containsKey( cr )){
                    TrieNode trieNode = node.getNodes().get( cr );
                    trieNode.getIndex().add( i );
                    node = trieNode;
                }else{
                    TrieNode trieNode = new TrieNode( cr );
                    trieNode.getIndex().add( i );
                    node.getNodes().put( cr,trieNode );
                    node = trieNode;
                }
            }
            node.setEnding( true );
            //反转字符串，构建后缀数
            String reverse = new StringBuffer( words[i] ).reverse().toString();
            node = suf;
            chars = reverse.toCharArray();
            for (char cr : chars) {
                if(node.getNodes().containsKey( cr )){
                    TrieNode trieNode = node.getNodes().get( cr );
                    trieNode.getIndex().add( i );
                    node = trieNode;
                }else{
                    TrieNode trieNode = new TrieNode( cr );
                    trieNode.getIndex().add( i );
                    node.getNodes().put( cr,trieNode );
                    node = trieNode;
                }
            }
            node.setEnding( true );

        }
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

    private void build1( String[] words ) {
        this.words =words;
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            for(int k=0;k<=word.length();k++){
                String suffix = word.substring( word.length()-k );
                suffixDicts.put( suffix+"#"+word,i );
            }
        }
    }


    public int f(String prefix, String suffix) {
        return f2( prefix, suffix );
    }

    private int f2( String prefix, String suffix ) {
        if(prefix.equals( "" )&&suffix.equals( "" )){
            return words.length-1;
        }
        Integer val = cache.get( prefix + "-" + suffix );
        if(val!=null){
            return val.intValue();
        }
        TrieNode node = pre;
        char[] chars = prefix.toCharArray();
        for (char cr : chars) {
            if(node.getNodes().containsKey( cr )){
                TrieNode trieNode = node.getNodes().get( cr );
                node = trieNode;
            }else{
                return -1;
            }
        }
        Set<Integer> s1 = node.getIndex();
        chars = suffix.toCharArray();
        node = suf;
        for (int i = chars.length - 1; i >= 0; i--) {
            char cr = chars[i];
            if(node.getNodes().containsKey( cr )){
                TrieNode trieNode = node.getNodes().get( cr );
                node = trieNode;
            }else{
                return -1;
            }

        }
        Set<Integer> s2 = node.getIndex();
        int res = -1;
        if(s1.isEmpty()){
            res =  (Integer)s2.toArray( )[s2.size()-1];
        }else if(s2.isEmpty()){
            res = (Integer)s1.toArray( )[s1.size()-1];
        }else{
            for (int i:s2) {
                if(!s1.contains(i))continue;
                res=Math.max(res,i);
            }

        }
        cache.put( prefix+"-"+suffix,res );
        return res;
    }

    private int method1( String prefix, String suffix ) {
        if(prefix.equals( "" )&&suffix.equals( "" )){
            return words.length-1;
        }
        Integer val = cache.get( prefix + "#" + suffix );
        if(val!=null){
            return val.intValue();
        }
        Set<String> keySet = null;
        int index = -1;
        Iterator<String> iterator = suffixDicts.keySet().iterator();
        if(prefix.equals( "" )){
            while(iterator.hasNext()){
                String key = iterator.next();
                if(key.startsWith( suffix+"#" )){
                    Integer var = suffixDicts.get( key );
                    if(var>index){
                        index = var;
                    }
                }
            }
        }else if(suffix.equals( "" )){
            while(iterator.hasNext()){
                String key = iterator.next();
                if(key.startsWith( prefix ) && key.split( "#" )[1].startsWith( prefix )){
                    Integer var = suffixDicts.get( key );
                    if(var>index){
                        index = var;
                    }
                }
            }
        }else{
            while(iterator.hasNext()){
                String key = iterator.next();
                if(key.startsWith( suffix+"#" ) && key.split( "#" )[1].startsWith( prefix )){
                    Integer var = suffixDicts.get( key );
                    if(var>index){
                        index = var;
                    }
                }
            }
        }
        cache.put( prefix+"#"+suffix,index );
        return index;
    }

    public static void main( String[] args ) {
        String[] words = {"abbbababbb","baaabbabbb","abababbaaa","abbbbbbbba","bbbaabbbaa","ababbaabaa","baaaaabbbb","babbabbabb","ababaababb","bbabbababa"};
        P745 p = new P745( words );
        List<Integer> result = new LinkedList<>(  );
        result.add( p.f( "","abaa" ) );
        result.add( p.f( "babbab","" ) );
        result.add( p.f( "ab","baaa" ) );
        result.add( p.f( "baaabba","b" ) );
        result.add( p.f( "abab","abbaabaa" ) );
        result.add( p.f( "","aa" ) );
        result.add( p.f( "","bba" ) );
        result.add( p.f( "","baaaaabbbb" ) );
        result.add( p.f( "ba","aabbbb") );
        result.add( p.f( "baaa","aabbabbb" ) );


    }
}
