package com.jvm;

/**
 * @author: wp
 * @Title: Father
 * @Description: TODO
 * @date 2020/5/21 21:09
 */
public class Father {
    static{
        System.out.print("(1)");
    }
    private int i = test();
    private static int test() {
        System.out.print("(4)");
        return 1;
    }

    private static int j = method();
    private static int method() {
        System.out.print("(5)");
        return 1;
    }


    public Father() {
        System.out.print("(2)");
    }
    {
        System.out.print("(3)");
    }

}

class Son extends Father{
    {
        System.out.print("(8)");
    }
    private int i = test();
    private static int test() {
        System.out.print("(9)");
        return 1;
    }

    private static int j = method();
    private static int method() {
        System.out.print("(10)");
        return 1;
    }

    static{
        System.out.print("(6)");
    }
    public Son() {
        System.out.print("(7)");
    }


    public static void main( String[] args ) {
        Son s1 = new Son();
        System.out.println();
        Son s2 = new Son();
    }
}
