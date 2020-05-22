package com.jvm;

/**
 * @author: wp
 * @Title: P045
 * @Description: 模拟对象的晋升 新生代-->老年代
 * @date 2020/5/3 18:54
 */
public class P045 {
    public static void main( String[] args ) {
        byte[] arr1 = new byte[2 * 1024 * 1024];
        arr1 = new byte[2 * 1024 * 1024];
        arr1 = new byte[2 * 1024 * 1024];
        arr1 = null;

        byte[] arr2 = new byte[128 * 1024];

        byte[] arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[2 * 1024 * 1024];
        arr3 = new byte[128 * 1024];
        arr3 = null;

        byte[] arr4 = new byte[2 * 1024 * 1024];

    }
}
