package com.leetCode;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author: wp
 * @Title: P1160
 * @Description: https://leetcode-cn.com/problems/find-words-that-can-be-formed-by-characters/
 *    题目：拼写单词 题号1160
 * @date 2020/3/17 21:43
 */
public class P1160 {
    public static int countCharacters( String[] words, String chars ) {
        int[] _char = splitByChar(chars);
        int sum = 0;
        for (String word : words) {
            int[] _word = splitByChar( word );
            if(isContains(_char,_word)){
                sum += word.length();
            }
        }
        return sum;
    }

    private static boolean isContains( int[] aChar, int[] word ) {
        for (int i = 0; i < aChar.length; i++) {
            if(aChar[i]<word[i]){
                return false;
            }
        }
        return true;
    }

    private static int[] splitByChar( String chars ) {
        int[] _char = new int[26];
        char[] array = chars.toCharArray();
        for (char c : array) {
            _char[c-'a']++;
        }
        return _char;
    }

    public static void main( String[] args ) {
        String[] words = {"dyiclysmffuhibgfvapygkorkqllqlvokosagyelotobicwcmebnpznjbirzrzsrtzjxhsfpiwyfhzyonmuabtlwin","ndqeyhhcquplmznwslewjzuyfgklssvkqxmqjpwhrshycmvrb","ulrrbpspyudncdlbkxkrqpivfftrggemkpyjl","boygirdlggnh","xmqohbyqwagkjzpyawsydmdaattthmuvjbzwpyopyafphx","nulvimegcsiwvhwuiyednoxpugfeimnnyeoczuzxgxbqjvegcxeqnjbwnbvowastqhojepisusvsidhqmszbrnynkyop","hiefuovybkpgzygprmndrkyspoiyapdwkxebgsmodhzpx","juldqdzeskpffaoqcyyxiqqowsalqumddcufhouhrskozhlmobiwzxnhdkidr","lnnvsdcrvzfmrvurucrzlfyigcycffpiuoo","oxgaskztzroxuntiwlfyufddl","tfspedteabxatkaypitjfkhkkigdwdkctqbczcugripkgcyfezpuklfqfcsccboarbfbjfrkxp","qnagrpfzlyrouolqquytwnwnsqnmuzphne","eeilfdaookieawrrbvtnqfzcricvhpiv","sisvsjzyrbdsjcwwygdnxcjhzhsxhpceqz","yhouqhjevqxtecomahbwoptzlkyvjexhzcbccusbjjdgcfzlkoqwiwue","hwxxighzvceaplsycajkhynkhzkwkouszwaiuzqcleyflqrxgjsvlegvupzqijbornbfwpefhxekgpuvgiyeudhncv","cpwcjwgbcquirnsazumgjjcltitmeyfaudbnbqhflvecjsupjmgwfbjo","teyygdmmyadppuopvqdodaczob","qaeowuwqsqffvibrtxnjnzvzuuonrkwpysyxvkijemmpdmtnqxwekbpfzs","qqxpxpmemkldghbmbyxpkwgkaykaerhmwwjonrhcsubchs"};
        String chars = "usdruypficfbpfbivlrhutcgvyjenlxzeovdyjtgvvfdjzcmikjraspdfp";
        int sum = countCharacters( words, chars );
        System.out.println(sum);
    }
}
