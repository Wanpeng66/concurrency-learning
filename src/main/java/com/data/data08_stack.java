package com.data;

import java.util.Stack;

/**
 * @author: wp
 * @Title: data08_stack
 * @Description: 栈是一种受限的线性表，只能从栈顶入栈或出栈，后进先出
 * 实际应用：方法栈、表达式求值、括号匹配
 * @date 2020/3/9 18:21
 */
public class data08_stack {


    public static void main( String[] args ) {
        example2();
    }

    //模拟匹配标签或括号
    //流程：从左往右读取，遇到左标签(括号)则入栈，遇到右标签(括号)则从栈顶出栈一个左标签进行匹配
    //如果对不上则不对，如果对上了则继续走，知道读取完，如果栈中还有元素则不对
    public static void example2(){
        String str = "[{[]()}[()]]{";
        boolean flag = false;
        Stack<Character> s1 = new Stack<>();
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if(aChar=='{'||aChar=='['||aChar=='('){
                s1.push( aChar );
            }else if(aChar==')'){
                if(s1.empty()){
                    flag = true;
                    break;
                }else{
                    Character pop = s1.pop();
                    if(pop!='('){
                        flag = true;
                        break;
                    }
                }
            }else if(aChar==']'){
                if(s1.empty()){
                    flag = true;
                    break;
                }else{
                    Character pop = s1.pop();
                    if(pop!='['){
                        flag = true;
                        break;
                    }
                }
            }else if(aChar=='}'){
                if(s1.empty()){
                    flag = true;
                    break;
                }else{
                    Character pop = s1.pop();
                    if(pop!='{'){
                        flag = true;
                        break;
                    }
                }
            }
        }
        if(!flag&&!s1.empty()){
            flag = true;
        }
        System.out.println(flag+"");
    }

    //模拟表达式求值：3+4*5-4/2
    //首先有两个栈，一个存储数字s1，一个存储操作符s2
    //流程：从左到右的读取表达式，遇到数字就压入s1，遇到操作符就压入s2
    //然后，每次压入操作符时要去s2中栈顶的操作符进行比较，如果比栈顶的操作符优先级高，则直接压入
    //否则，取出s2栈顶的操作符，从s1中取上面两个数进行计算，重新压入s1
    //然后再比较s2栈顶的操作符与之前要压入的操作符的优先级,直到高于栈顶操作符
    private static void example1() {
        String str = "3*4*5-4/2+2";
        Stack<Integer> s1 = new Stack<>();
        Stack<Character> s2 = new Stack<>();
        char[] chars = str.toCharArray();
        for (char aChar : chars) {
            if(aChar == '+' || aChar == '-' || aChar == '*' || aChar == '/'){
                if(s2.empty()){
                    s2.push( aChar );
                }else{
                    compute( s1, s2, aChar );
                }
            }else{
                s1.push( Integer.parseInt( aChar+"" ) );
            }
        }
        while(!s2.empty()){
            Integer n1 = s1.pop();
            Integer n2 = s1.pop();
            Character c = s2.pop();
            if(c=='+'){
                s1.push( n1+n2 );
            }else if(c=='-'){
                s1.push( n2-n1 );
            }else if(c=='*'){
                s1.push( n1*n2 );
            }else{
                s1.push( n2/n1 );
            }
        }
        System.out.println(s1.pop());
    }

    private static void compute( Stack<Integer> s1, Stack<Character> s2, char aChar ) {
        Character peek = null;
        try{
            peek = s2.pop();
        }catch(Exception e){
            s2.push( aChar );
            return ;
        }
        switch(peek){
            case '+':
                //如果要入栈的操作符比栈顶的操作符优先级高，直接入栈
                if(aChar == '*' || aChar == '/'){
                    s2.push( peek );
                    s2.push( aChar );
                }else{
                    Integer n1 = s1.pop();
                    Integer n2 = s1.pop();
                    s1.push( n1+n2 );
                    compute( s1,s2,aChar );
                }
                break;
            case '-':
                if(aChar == '*' || aChar == '/'){
                    s2.push( peek );
                    s2.push( aChar );
                }else{
                    Integer n1 = s1.pop();
                    Integer n2 = s1.pop();
                    s1.push( n2-n1 );
                    compute( s1,s2,aChar );
                }
                break;
            case '*':
                    Integer n1 = s1.pop();
                    Integer n2 = s1.pop();
                    s1.push( n1*n2 );
                    compute( s1,s2,aChar );
                break;
            case '/':
                Integer n11 = s1.pop();
                Integer n22 = s1.pop();
                s1.push( n22/n11 );
                compute( s1,s2,aChar );

                break;

        }
    }


}
