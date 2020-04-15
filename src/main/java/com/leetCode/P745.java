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
    Map<String,Integer> prefixDicts = new HashMap<>(  );
    Map<String,Integer> suffixDicts = new HashMap<>(  );
    TrieNode pre = new TrieNode( '/' );
    TrieNode suf = new TrieNode( '/' );
    String[] words ;
    public P745(String[] words) {
        this.words = words;
        buildTrie( words );
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
            String reverse = reverse( words[i] );
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
                String prefix = word.substring( 0,k );
                String suffix = word.substring( word.length()-k );
                prefixDicts.put( prefix+"#"+word,i );
                suffixDicts.put( suffix+"#"+word,i );
            }
        }
    }


    public int f(String prefix, String suffix) {
        if(prefix.equals( "" )&&suffix.equals( "" )){
            return words.length-1;
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
        if(s1.isEmpty()&&s2.isEmpty()){
            return -1;
        } else if(s1.isEmpty()){
            return s2.stream().sorted(Comparator.reverseOrder()).collect( Collectors.toList()).get( 0 );
        }else if(s2.isEmpty()){
            return s1.stream().sorted( Comparator.reverseOrder() ).collect( Collectors.toList() ).get( 0 );
        }else{
            List<Integer> collect = s1.stream().filter( integer -> s2.contains( integer ) ).sorted( Comparator.reverseOrder() ).collect( Collectors.toList() );
            if(collect.isEmpty()){
                return -1;
            }else{
                return collect.get( 0 );
            }
        }
    }

    private int method1( String prefix, String suffix ) {
        if(prefix.equals( "" )&&suffix.equals( "" )){
            return words.length-1;
        }
        Set<String> keySet = null;
        if(prefix.equals( "" )){
            keySet = suffixDicts.keySet();
            Set<Integer> collect = keySet.stream().filter( s -> s.startsWith( suffix + "#" ) ).
                    map( s -> suffixDicts.get( s ) ).sorted( Comparator.reverseOrder() ).
                    collect( Collectors.toCollection( LinkedHashSet :: new ) );
            if(collect.isEmpty()){
                return -1;
            }else{
                return collect.iterator().next();
            }

        }else if(suffix.equals( "" )){
            keySet = prefixDicts.keySet();
            Set<Integer> collect = keySet.stream().filter( s -> s.startsWith( prefix + "#" ) ).
                    map( s -> prefixDicts.get( s ) ).sorted( Comparator.reverseOrder() ).
                    collect( Collectors.toCollection( LinkedHashSet :: new ) );
            if(collect.isEmpty()){
                return -1;
            }else{
                return collect.iterator().next();
            }
        }else{
            keySet = suffixDicts.keySet();
            Set<Integer> l1 = keySet.stream().filter( s -> s.startsWith( suffix + "#" ) ).
                    map( s -> suffixDicts.get( s ) ).sorted( Comparator.reverseOrder() ).
                    collect( Collectors.toCollection( LinkedHashSet :: new ) );
            keySet = prefixDicts.keySet();
            Set<Integer> l2 = keySet.stream().filter( s -> s.startsWith( prefix + "#" ) ).
                    map( s -> prefixDicts.get( s ) ).sorted( Comparator.reverseOrder() ).
                    collect( Collectors.toCollection( LinkedHashSet :: new ) );
            if(l1.isEmpty()||l2.isEmpty()){
                return -1;
            }else{
                Set<Integer> collect = l1.stream().filter( val -> l2.contains( val ) ).
                        sorted( Comparator.reverseOrder() ).
                        collect( Collectors.toCollection( LinkedHashSet :: new ) );
                if(collect.isEmpty()){
                    return -1;
                }else{
                    return collect.iterator().next();
                }
            }
        }
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
